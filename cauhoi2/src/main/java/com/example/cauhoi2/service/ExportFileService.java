package com.example.cauhoi2.service;


import com.example.cauhoi2.dto.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class ExportFileService {
    public byte[] exportManyExamsToZip(String name, List<ExamResponse> exams) {
        try (ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(zipOut)) {

            for (int i = 0; i < exams.size(); i++) {
                ExamResponse exam = exams.get(i);
                XWPFDocument doc = createWordDoc(exam, i);

                ByteArrayOutputStream docOut = new ByteArrayOutputStream();
                doc.write(docOut);
                doc.close();

                String fileName = name.replaceAll("\\s+", "_") + "_De_" + (i + 1) + ".docx";
                ZipEntry entry = new ZipEntry(fileName);
                zos.putNextEntry(entry);
                zos.write(docOut.toByteArray());
                zos.closeEntry();
            }

            // Ghi file đáp án tổng hợp
            XWPFDocument answerDoc = createAnswerDocument(exams);
            ByteArrayOutputStream answerOut = new ByteArrayOutputStream();
            answerDoc.write(answerOut);
            answerDoc.close();

            String answerFileName = name.replaceAll("\\s+", "_") + "_Dap_An.docx";
            ZipEntry answerEntry = new ZipEntry(answerFileName);
            zos.putNextEntry(answerEntry);
            zos.write(answerOut.toByteArray());
            zos.closeEntry();

            zos.finish();
            return zipOut.toByteArray();

        } catch (IOException e) {
            log.info("Error: {}", e.getMessage());
            throw new RuntimeException("Lỗi khi tạo file ZIP", e);
        }
    }

    private XWPFDocument createWordDoc(ExamResponse exam, int index) {
        XWPFDocument doc = new XWPFDocument();

        // Tiêu đề đề thi
        XWPFParagraph title = doc.createParagraph();
        XWPFRun runTitle = title.createRun();
        runTitle.setText("Đề " + (index + 1));
        runTitle.setBold(true);
        runTitle.setFontSize(16);
        runTitle.setFontFamily("Arial"); // chọn font Arial
        int maxQuestion = 1;
        for (GroupResponse group : exam.getGroups()) {
            for (DescriptionResponse desc : group.getDescriptions()) {
                writeRunParts(doc, desc.getContents());
            }

            for (QuestionResponse question : group.getQuestions()) {
                XWPFParagraph qPara = doc.createParagraph();
                XWPFRun qRun = qPara.createRun();
                qRun.setText("Câu hỏi " + maxQuestion++ + ": ");
                qRun.setBold(true);
                qRun.setFontFamily("Arial");
                qRun.setFontSize(12);
                writeRunParts(doc, question.getContents(), qPara);

                for (int i = 0; i < question.getAnswers().size(); i++) {
                    AnswerResponse answer = question.getAnswers().get(i);
                    XWPFParagraph aPara = doc.createParagraph();
                    XWPFRun aRun = aPara.createRun();
                    aRun.setBold(true);
                    aRun.setText((char)('A' + i) + ". ");
                    aRun.setFontFamily("Arial");
                    aRun.setFontSize(12);
                    writeRunParts(doc, answer.getContents(), aPara);
                }
            }
        }

        return doc;
    }

    private XWPFDocument createAnswerDocument(List<ExamResponse> exams) {
        XWPFDocument doc = new XWPFDocument();

        for (int i = 0; i < exams.size(); i++) {
            ExamResponse exam = exams.get(i);
            XWPFParagraph title = doc.createParagraph();
            XWPFRun runTitle = title.createRun();
            runTitle.setText("Đề " + (i + 1));
            runTitle.setBold(true);
            int sttQuestion = 1;
            for (GroupResponse group : exam.getGroups()) {
                for (QuestionResponse question : group.getQuestions()) {
                    XWPFParagraph questionPara = doc.createParagraph();
                    XWPFRun questionRun = questionPara.createRun();
                    questionRun.setText("Câu " + sttQuestion++ + ": ");

                    for (int j = 0; j < question.getAnswers().size(); j++) {
                        AnswerResponse answer = question.getAnswers().get(j);
                        if (answer.isAnswer()) {
                            questionRun.setText((char) ('A' + j) + ". " + answer.getContents().stream().map(RunPartResponse::getText).collect(Collectors.joining()));
                            break;
                        }
                    }
                }
            }

            doc.createParagraph().createRun().addBreak();
        }

        return doc;
    }

    private void writeRunParts(XWPFDocument doc, List<RunPartResponse> parts) {
        writeRunParts(doc, parts, doc.createParagraph());
    }

    private void writeRunParts(XWPFDocument doc, List<RunPartResponse> parts, XWPFParagraph paragraph) {
        //
        for (RunPartResponse run : parts) {
            XWPFRun xRun = paragraph.createRun();
            xRun.setText(run.getText());
            xRun.setFontSize(12);
            xRun.setFontFamily("Arial");
            xRun.setColor("000000");

            if (run.isToDam()) xRun.setBold(true);
            if (run.isGachChan()) xRun.setUnderline(UnderlinePatterns.SINGLE);
            if (run.isInNghieng()) xRun.setItalic(true);

            // Thêm ảnh nếu có
            for (ImageResponse img : run.getImages()) {
                try {
                    String imagePath = img.getLink().substring(1);
                    File file = new File(imagePath);
                    InputStream is = new FileInputStream(file);
                    xRun.addPicture(is,
                            XWPFDocument.PICTURE_TYPE_PNG,
                            img.getId() + ".png",
                            Units.toEMU(run.getWidth() != null ? run.getWidth() : 200),
                            Units.toEMU(run.getHeight() != null ? run.getHeight() : 100));
                    is.close();
                } catch (Exception e) {
                    xRun.setText("[Không thể tải ảnh]");
                }
            }

            if (run.isEndLine()) {
                xRun.addCarriageReturn();
            }
        }
    }
}
