package com.example.cauhoi2.analyst;

import com.example.cauhoi2.dto.request.word_file.PartAnalyst;
import com.example.cauhoi2.entity.Image;
import com.example.cauhoi2.entity.file_data.*;
import com.example.cauhoi2.mapper.ImageMapper;
import com.example.cauhoi2.service.ImageService;
import com.example.cauhoi2.util.ExamUtil;
import com.example.cauhoi2.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ExamAnalyst {
    @Autowired
    ImageService imageService;
    @Autowired
    ImageMapper imageMapper;
    private List<Answer> analystAnswer(XWPFParagraph part) {
        List<Answer> answers = new ArrayList<>();
        for (XWPFRun run : part.getRuns()) {
            String runToString = run.toString();
            if (runToString.contains("\t")) {
                Answer lastAnswer = answers.isEmpty() ? null : answers.getLast();
                if (lastAnswer == null) {
                    lastAnswer = new Answer();
                    answers.add(lastAnswer);
                }
                if (runToString.equals("\t")) {
                    if (!lastAnswer.getContents().isEmpty())
                        answers.add(new Answer());
                    continue;
                }
                RunPart runPart = ExamUtil.copy(run);
                runPart.setText(String.join("", runPart.getText().split("\t")));

                if (runToString.endsWith("\t"))
                    lastAnswer.getContents().add(runPart);
                else if (runToString.startsWith("\t"))
                {
                    if (!lastAnswer.getContents().isEmpty()) {
                        Answer newAnswer = new Answer();
                        newAnswer.getContents().add(runPart);
                        answers.add(newAnswer);
                    } else {
                        lastAnswer.getContents().add(runPart);
                    }
                }
            }
            else {
                if (answers.isEmpty()) answers.add(new Answer());
                answers.getLast().getContents().add(ExamUtil.copy(run));
            }
        }
        return answers;
    }
    public Question analystQuestion(PartAnalyst part) {
        if (!part.isQuestion()) return null;
        Question question = new Question();
        for (XWPFParagraph line : part.getValues()) {
            if (ExamUtil.isFirstAnswer(line.getText())) {
                List<Answer> answers = analystAnswer(line);
                question.getAnswers().addAll(answers);
            } else {
                for (XWPFRun run : line.getRuns()) {
                    question.getContents().add(analystContent(run));
                }
                //Thêm dấu xuống dòng
                question.getContents().add(RunPart
                    .builder()
                    .isEndLine(true)
                    .build());
            }
        }
        return question;
    }
    public RunPart analystContent(XWPFRun run) {
        RunPart runPart = new RunPart();
        List<XWPFPicture> pictures = run.getEmbeddedPictures();
        if (pictures.isEmpty()) {
            runPart = ExamUtil.copy(run);
        }
        else {
            runPart.setType(RunPartType.IMAGE);
            for (XWPFPicture pic : pictures) {
                XWPFPictureData picData = pic.getPictureData();
                byte[] imageBytes = picData.getData();

                // Tạo tên file duy nhất
                String fileName = "image_" + System.currentTimeMillis() + "." + picData.suggestFileExtension();
                runPart.getImages().add(imageService.saveImageToService(Util.createMultipartFile(imageBytes, fileName)));
            }
        }
        return runPart;
    }
}
