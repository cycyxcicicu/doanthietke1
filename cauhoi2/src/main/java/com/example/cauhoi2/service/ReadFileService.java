package com.example.cauhoi2.service;

import com.example.cauhoi2.analyst.ExamAnalyst;
import com.example.cauhoi2.dto.request.GroupAnalystRequest;
import com.example.cauhoi2.dto.request.PartAnalystRequest;
import com.example.cauhoi2.entity.*;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.repository.ExamRepository;
import com.example.cauhoi2.util.ExamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ReadFileService {
    @Autowired
    ExamAnalyst examAnalyst;
    @Autowired
    ExamRepository examRepository;
    public String readFile(MultipartFile file) {
        List<GroupAnalystRequest> groups = parseGroupsFromFile(file);
        Exam exam = buildExamFromGroupRequests(groups,
            Objects.requireNonNullElse(file.getOriginalFilename(), "Exam").replace(".docx", ""));
        examRepository.save(exam);
        return exam.getId();
    }

    private List<GroupAnalystRequest> parseGroupsFromFile(MultipartFile file) {
        List<GroupAnalystRequest> groups = new ArrayList<>();
        try {
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            GroupAnalystRequest currentGroup = null;

            for (XWPFParagraph para : doc.getParagraphs()) {
                String text = para.getText().trim();
                String groupName = ExamUtil.getGroup(text);

                if (!groupName.equals("NONE")) {
                    if (currentGroup != null) groups.add(currentGroup);
                    currentGroup = createNewGroupRequest(groupName);
                } else {
                    if (currentGroup == null) {
                        currentGroup = createNewGroupRequest("NONE");
                    }

                    PartAnalystRequest lastPart = currentGroup.getParts().isEmpty() ? null : currentGroup.getParts().getLast();

                    if (ExamUtil.isFirstQuestion(text)) {
                        PartAnalystRequest part = new PartAnalystRequest();
                        part.setQuestion(true);
                        part.getValues().add(para);
                        currentGroup.getParts().add(part);
                    } else if (!ExamUtil.isFirstAnswer(text)) {
                        if (lastPart != null && !lastPart.isAddedAnswered())
                            lastPart.getValues().add(para);
                        else {
                            if (lastPart != null) {
                                groups.add(currentGroup);
                                currentGroup = createNewGroupRequest("NONE");
                            }
                            PartAnalystRequest part = new PartAnalystRequest();
                            part.getValues().add(para);
                            currentGroup.getParts().add(part);
                        }
                    } else {
                        // Là câu trả lời
                        if (lastPart != null) {
                            lastPart.getValues().add(para);
                            lastPart.setAddedAnswered(true);
                        }
                    }
                }
            }

            if (currentGroup != null) groups.add(currentGroup);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CANNOT_READ_FILE);
        }

        return groups;
    }

    private Exam buildExamFromGroupRequests(List<GroupAnalystRequest> groups, String fileName) {
        Exam exam = new Exam();
        exam.setName(fileName);

        int[] maxStt = {1, 1, 1, 1};

        for (GroupAnalystRequest groupAnalyst : groups) {
            Group group = createGroup(groupAnalyst, maxStt[0]++);
            maxStt[1] = 1; // reset question stt
            maxStt[2] = 1; // reset description stt

            for (PartAnalystRequest part : groupAnalyst.getParts()) {
                if (part.isQuestion()) {
                    Question question = processQuestion(part, group, maxStt[1]++);
                    maxStt[3] = 1; // reset answer stt
                    for (Answer answer : question.getAnswers()) {
                        answer.setQuestion(question);
                        answer.setStt(maxStt[3]++);
                        setSttRunPart(answer.getContents());
                    }
                    group.getQuestions().add(question);
                } else {
                    Description description = processDescription(part, group, maxStt[2]++);
                    group.getDescriptions().add(description);
                }
            }

            exam.getGroups().add(group);
        }

        return exam;
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
        cleanQuestionContent(question);
        cleanQuestionAnswers(question);

        validateAnswerRules(question);
    }

    private void validateAnswerRules(Question question) {
        // check câu hỏi phải có tối thiểu 2 câu trả lời
        if (question.getAnswers().size() < 2)
            throw new AppException(ErrorCode.EXIST_QUESTION_HAS_UNDER_2_ANSWER);

        // check câu hỏi chỉ có 1 đáp án duy nhất
        boolean hasAnswered = false;
        for (Answer answer : question.getAnswers())
            if (answer.isAnswer()) {
                if (hasAnswered)
                    throw new AppException(ErrorCode.EXIST_QUESTION_HAS_MANY_ANSWERS_CORRECT);
                hasAnswered = true;
            }

        // không có đáp án nào đúng
        if (!hasAnswered)
            throw new AppException(ErrorCode.EXIST_QUESTION_HAS_NO_ANSWER_CORRECT);
    }

    private void cleanQuestionAnswers(Question question) {

        for (Answer answer : question.getAnswers()) {
            // set các thuộc tính cho câu trả lời
            if (!answer.getContents().isEmpty()) {
                RunPart partFirst = answer.getContents().getFirst();
                if (ExamUtil.checkAnswerCorrect(partFirst))
                    answer.setAnswer(true);
                if (ExamUtil.checkAnswerNotMix(partFirst))
                    answer.setNotMix(true);
            }

            // Loại bỏ phần đầu câu trả lời VD: A.
            while (!answer.getContents().isEmpty()) {
                RunPart runPart = answer.getContents().getFirst();
                if (ExamUtil.containsPunctuation(runPart.getText())) {
                    runPart.setText(runPart.getText().split("[.:]", 2)[1]);
                    if (runPart.getText().trim().isEmpty())
                        answer.getContents().removeFirst();
                    break;
                }
                answer.getContents().removeFirst();
            }

            // Loại bỏ các khoảng trống ở đầu
            if (!answer.getContents().isEmpty()) {
                RunPart runPartFirst = answer.getContents().getFirst();
                runPartFirst.setText(runPartFirst.getText().replaceFirst("^\\s+", ""));
            }
//            if (!answer.isAnswer() && answer.getContents().stream()
//                .map(RunPart::getText)
//                .noneMatch(text -> text != null && !text.trim().isEmpty())) {
//                answers.remove();
//            }
        }
    }

    private void cleanQuestionContent(Question question) {
        Iterator<RunPart> contents = question.getContents().iterator();

        // Loại phần đầu "Câu 1."
        while (contents.hasNext()) {
            RunPart part = contents.next();
            if (ExamUtil.containsPunctuation(part.getText())) {
                part.setText(part.getText().split("[.:]", 2)[1]);
                if (part.getText().trim().isEmpty()) contents.remove();
                break;
            }
            contents.remove();
        }

        // Loại khoảng trắng thừa
        while (contents.hasNext()) {
            String text = contents.next().getText();
            if (text == null || text.trim().isEmpty()) contents.remove();
            else break;
        }

        if (!question.getContents().isEmpty()) {
            RunPart first = question.getContents().getFirst();
            first.setText(first.getText().replaceFirst("^\\s+", ""));
        }
    }

    private GroupAnalystRequest createNewGroupRequest(String name) {
        GroupAnalystRequest group = new GroupAnalystRequest();
        group.setName(name);
        return group;
    }

    private Group createGroup(GroupAnalystRequest groupAnalyst, int stt) {
        Group group = new Group();
        String name = groupAnalyst.getName();
        if (name.startsWith("#")) {
            group.setNotMix(true);
            name = name.replace("#", "");
        }
        group.setName(name);
        group.setStt(stt);
        return group;
    }

    private Question processQuestion(PartAnalystRequest part, Group group, int stt) {
        Question question = examAnalyst.analystQuestion(part);
        settingQuestion(question);
        question.setStt(stt);
        question.setGroup(group);
        setSttRunPart(question.getContents());
        return question;
    }

    private Description processDescription(PartAnalystRequest part, Group group, int stt) {
        Description desc = examAnalyst.analystDescription(part);
        desc.setStt(stt);
        desc.setGroup(group);
        setSttRunPart(desc.getContents());
        return desc;
    }
}
