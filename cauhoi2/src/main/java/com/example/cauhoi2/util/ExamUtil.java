package com.example.cauhoi2.util;

import com.example.cauhoi2.entity.file_data.RunPart;
import com.example.cauhoi2.entity.file_data.RunPartType;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamUtil {
    public static boolean isFirstQuestion(String text) {
        return text != null && text.trim().matches("(?i)^\\s*(Câu|Question)\\s*\\d+[:.\\s].*");
    }
    public static boolean isFirstAnswer(String text) {
        return text != null && text.trim().matches("^(#?[A-Z])\\..*");
    }
    static XWPFParagraph copy(XWPFDocument targetDoc, XWPFParagraph srcPara) {
        XWPFParagraph newPara = targetDoc.createParagraph();
        newPara.getCTP().set(srcPara.getCTP().copy());
        return newPara;
    }
    public static String getGroup(String line) {
        if (line == null) return "NONE";
        Matcher matcher = Pattern.compile("^<g(\\d)>").matcher(line.trim());
        return matcher.find() ? "g" + matcher.group(1) : "NONE";
    }
    public static RunPart copy(XWPFRun run) {
        return RunPart.builder()
            .type(RunPartType.TEXT)
            .text(run.toString())
            .isToDam(run.isBold())
            .isGachChan(run.getUnderline() != UnderlinePatterns.NONE)
            .maMauChu(run.getColor())
            .build();
    }
    public static boolean containsPunctuation(String text) {
        return text != null && (text.contains(":") || text.contains("."));
    }
    public static boolean checkAnswerCorrect(RunPart runPart) {
        return runPart.isGachChan() || (runPart.getMaMauChu() != null && runPart.getMaMauChu().equals("FF0000"));
    }
    public static boolean checkAnswerNotMix(RunPart runPart) {
        return runPart.getText().trim().startsWith("#");
    }
}
