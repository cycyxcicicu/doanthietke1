package com.example.cauhoi2.service;

import com.example.cauhoi2.analyst.ExamAnalyst;
import com.example.cauhoi2.dto.request.word_file.GroupAnalyst;
import com.example.cauhoi2.dto.request.word_file.PartAnalyst;
import com.example.cauhoi2.entity.Image;
import com.example.cauhoi2.entity.file_data.*;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.repository.ExamRepository;
import com.example.cauhoi2.util.ExamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class ReadFileService {
    @Autowired
    ExamAnalyst examAnalyst;
    @Autowired
    ExamRepository examRepository;
    public String readFile(MultipartFile file) {
        List<GroupAnalyst> groupsAnalyst = new ArrayList<>();

        // bóc tách dữ liệu trong file
        try {
            XWPFDocument doc = new XWPFDocument(file.getInputStream());

            GroupAnalyst groupAnalyst = null;

            //Gom nhóm thành từng group, gom và phân biệt từng câu hỏi, câu trả lời hay chú thích, yêu cầu, đề bài
            for (int i = 0; i < doc.getParagraphs().size() - 1; i++) {
                XWPFParagraph para = doc.getParagraphs().get(i);
                String paraText = para.getText();
                String groupName = ExamUtil.getGroup(paraText);
                if (!groupName.equals("NONE"))
                {
                    if (groupAnalyst != null)
                        groupsAnalyst.add(groupAnalyst);
                    groupAnalyst = new GroupAnalyst();
                    groupAnalyst.setName(groupName);
                } else {
                    if (groupAnalyst == null) {
                        groupAnalyst = new GroupAnalyst();
                        groupAnalyst.setName("NONE");
                    }
                    PartAnalyst lastPart = !groupAnalyst.getParts().isEmpty() ? groupAnalyst.getParts().getLast() : null;
                    if (ExamUtil.isFirstQuestion(paraText)) {
                        PartAnalyst part = new PartAnalyst();
                        part.getValues().add(para);
                        part.setQuestion(true);
                        groupAnalyst.getParts().add(part);
                    } else if (lastPart != null) {
                        if (!ExamUtil.isFirstAnswer(paraText)) {
                            if (lastPart.isQuestion() && lastPart.isAddedAnswered()) {
                                PartAnalyst part = new PartAnalyst();
                                part.getValues().add(para);
                                groupAnalyst.getParts().add(part);
                            } else
                                lastPart.getValues().add(para);
                        } else {
                            lastPart.getValues().add(para);
                            lastPart.setAddedAnswered(true);
                        }
                    }
                }
            }

            groupsAnalyst.add(groupAnalyst);
        } catch (IOException e) {throw new AppException(ErrorCode.CANNOT_READ_FILE);}

        // phân tích và lưu dữ liệu
        Exam exam = new Exam();
        exam.setName(file.getName());
        for (GroupAnalyst groupAnalyst : groupsAnalyst) {
            Group newGroup = new Group();
            newGroup.setName(groupAnalyst.getName());
            for (PartAnalyst part : groupAnalyst.getParts()) {
                if (part.isQuestion()) {
                    Question question = examAnalyst.analystQuestion(part);
                    settingQuestion(question);
                    newGroup.getQuestions().add(question);
                }
                else {
                    DescriptionQuestion descriptionQuestion = new DescriptionQuestion();
                    for (XWPFParagraph partOfPart : part.getValues()) {
                        for (XWPFRun run : partOfPart.getRuns()) {
                            descriptionQuestion.getContents().add(examAnalyst.analystContent(run));
                        }
                        //Thêm dấu xuống dòng
                        descriptionQuestion.getContents().add(RunPart
                            .builder()
                            .isEndLine(true)
                            .build());
                    }
                    newGroup.getItems().add(descriptionQuestion);
                }
            }
            exam.getGroups().add(newGroup);
        }
        int maxSttGroup = 1;
        for (Group group : exam.getGroups()) {
            group.setStt(maxSttGroup++);
            int maxStt = 1;
            for (DescriptionQuestion descriptionQuestion : group.getItems()) {
                descriptionQuestion.setStt(maxStt++);
                setSttRunPart(descriptionQuestion.getContents());
            }
            maxStt = 1;
            for (Question question : group.getQuestions()) {
                question.setStt(maxStt++);
                setSttRunPart(question.getContents());
                int maxSttAnswer = 1;
                for (Answer answer : question.getAnswers()) {
                    answer.setStt(maxSttAnswer++);
                    setSttRunPart(answer.getContents());
                }
            }
        }
        examRepository.save(exam);
        return exam.getId();
    }

    void setSttRunPart(List<RunPart> runParts) {
        int maxStt = 1;
        for (RunPart runPart : runParts) {
            runPart.setStt(maxStt++);
            if (runPart.getImages() == null) continue;
            int maxSttImage = 1;
            for (Image image : runPart.getImages())
                image.setStt(maxSttImage++);
        }
    }

    void settingQuestion(Question question) {
        if (question == null) return;
        Iterator<RunPart> contents = question.getContents().iterator();
        while (contents.hasNext()) {
            RunPart runPartFirst = contents.next();
            if (ExamUtil.containsPunctuation(runPartFirst.getText())) {
                runPartFirst.setText(runPartFirst.getText().split("[.:]", 2)[1]);
                break;
            }
            contents.remove();
        }
        while (contents.hasNext() && contents.next().getText().trim().isEmpty()) {
            contents.remove();
        }
        if (!question.getContents().isEmpty()) {
            RunPart runPartFirst = question.getContents().getFirst();
            runPartFirst.setText(runPartFirst.getText().replaceFirst("^\\s+", ""));
            question.getContents().removeLast();
        }
        Iterator<Answer> answers = question.getAnswers().iterator();
        while (answers.hasNext()) {
            Answer answer = answers.next();
            if (!answer.getContents().isEmpty()) {
                RunPart partFirst = answer.getContents().getFirst();
                if (ExamUtil.checkAnswerCorrect(partFirst))
                    answer.setAnswer(true);
                if (ExamUtil.checkAnswerNotMix(partFirst))
                    answer.setNotMix(true);
            }
            while (!answer.getContents().isEmpty()) {
                RunPart runPart = answer.getContents().getFirst();
                if (ExamUtil.containsPunctuation(runPart.getText())) {
                    runPart.setText(runPart.getText().split("[.:]", 2)[1]);
                    break;
                }
                answer.getContents().removeFirst();
            }
            if (!answer.getContents().isEmpty()) {
                RunPart runPartFirst = answer.getContents().getFirst();
                runPartFirst.setText(runPartFirst.getText().replaceFirst("^\\s+", ""));
            }
            if (answer.getContents().stream()
                    .map(RunPart::getText)
                    .noneMatch(text -> text != null && !text.trim().isEmpty())) {
                answers.remove();
            }
        }
    }
}
