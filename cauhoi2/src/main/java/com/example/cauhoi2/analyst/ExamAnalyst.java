package com.example.cauhoi2.analyst;

import com.example.cauhoi2.dto.request.PartAnalystRequest;
import com.example.cauhoi2.entity.*;
import com.example.cauhoi2.service.ImageService;
import com.example.cauhoi2.util.ExamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTransform2D;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ExamAnalyst {
    @Autowired
    ImageService imageService;
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
        for (Answer answer : answers) {
            // Gộp các run
            List<RunPart> newContents = new ArrayList<>();
            RunPart now = null;
            for (RunPart runPart : answer.getContents()) {
                if (now == null)
                    now = runPart;
                else if (now.getType() == RunPartType.IMAGE) {
                    newContents.add(now);
                    now = runPart;
                }
                else if (ExamUtil.canMerge(now, runPart)) {
                    now.setText(now.getText() + runPart.getText());
                } else {
                    newContents.add(now);
                    now = runPart;
                }
            }
            if (now != null) newContents.add(now);
            answer.setContents(newContents);
        }
        return answers;
    }
    public Question analystQuestion(PartAnalystRequest part) {
        if (!part.isQuestion()) return null;
        Question question = new Question();
        for (XWPFParagraph line : part.getValues()) {
            if (ExamUtil.isFirstAnswer(line.getText())) {
                List<Answer> answers = analystAnswer(line);
                question.getAnswers().addAll(answers);
            } else {
                XWPFRun runMerge = null;
                RunPart runPartNow = null;
                // Gom nhóm các run có style tương đồng để giảm thiểu không gian lưu trữ
                for (XWPFRun run : line.getRuns()) {
                    if (runPartNow == null) {
                        runPartNow = analystContent(run);
                        runMerge = run;
                    }
                    else if (runPartNow.getType() == RunPartType.IMAGE) {
                        question.getContents().add(runPartNow);
                        runPartNow = analystContent(run);
                        runMerge = run;
                    }
                    else if (ExamUtil.canMerge(runMerge, run))
                        runPartNow.setText(runPartNow.getText() + run.toString());
                    else {
                        question.getContents().add(runPartNow);
                        runPartNow = analystContent(run);
                        runMerge = run;
                    }
                }
                if (runPartNow != null)
                    question.getContents().add(runPartNow);
                //Thêm dấu xuống dòng
                question.getContents().add(RunPart
                    .builder()
                    .isEndLine(true)
                    .build());
            }
        }

        question.getContents().removeLast();
        return question;
    }
    public Description analystDescription(PartAnalystRequest partAnalystRequest) {
        Description description = new Description();
        for (XWPFParagraph partOfPart : partAnalystRequest.getValues()) {
            // Gom nhóm các run có style tương đồng để giảm thiểu không gian lưu trữ
            XWPFRun runMerge = null;
            RunPart runPartNow = null;
            for (XWPFRun run : partOfPart.getRuns()) {
                if (runPartNow == null) {
                    runPartNow = analystContent(run);
                    runMerge = run;
                }
                else if (runPartNow.getType() == RunPartType.IMAGE) {
                    description.getContents().add(runPartNow);
                    runPartNow = analystContent(run);
                    runMerge = run;
                }
                else if (ExamUtil.canMerge(runMerge, run))
                    runPartNow.setText(runPartNow.getText() + run.toString());
                else {
                    description.getContents().add(runPartNow);
                    runPartNow = analystContent(run);
                    runMerge = run;
                }
            }
            if (runPartNow != null)
                description.getContents().add(runPartNow);
            //Thêm dấu xuống dòng
            description.getContents().add(RunPart
                .builder()
                .isEndLine(true)
                .build());
        }
        description.getContents().removeLast();
        return description;
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
                runPart.getImages().add(imageService.saveImageToLocal(imageBytes, fileName, runPart.getImages().size()));

                CTPicture ctPic = pic.getCTPicture();
                if (ctPic != null && ctPic.getSpPr() != null && ctPic.getSpPr().getXfrm() != null) {
                    CTTransform2D xfrm = ctPic.getSpPr().getXfrm();
                    if (xfrm.getExt() != null) {
                        long cx = xfrm.getExt().getCx(); // Chiều rộng (EMU)
                        long cy = xfrm.getExt().getCy(); // Chiều cao (EMU)

                        // Chuyển EMU -> pixel (1 pixel = 9525 EMU)
                        int widthPx = (int) (cx / 9525);
                        int heightPx = (int) (cy / 9525);

                        runPart.setWidth(widthPx);
                        runPart.setHeight(heightPx);
                    }
                }
            }
        }
        return runPart;
    }
}
