package com.example.cauhoi2.util;

import com.example.cauhoi2.entity.RunPart;
import com.example.cauhoi2.entity.RunPartType;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        Matcher matcher = Pattern.compile("^<(#?)g(\\d+)>").matcher(line.trim());
        return matcher.find() ? matcher.group(1) + "g" + matcher.group(2) : "NONE";
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
        return runPart.getText() != null && runPart.getText().trim().startsWith("#");
    }

    /*
        boolean isToDam;
    boolean isGachChan;
    boolean isInNghieng;
    boolean isEndLine;
    String maMauChu;
    */
    public static boolean canMerge(XWPFRun run1, XWPFRun run2) {
        return run1.isBold() == run2.isBold() &&
            run1.isItalic() == run2.isItalic() &&
            run1.getUnderline() == run2.getUnderline() &&
            Objects.equals(Optional.ofNullable(run1.getColor()).orElse("000000"),
                Optional.ofNullable(run2.getColor()).orElse("000000"));

    }
    public static boolean canMerge(RunPart run1, RunPart run2) {
        return run1.isToDam() == run2.isToDam() &&
            run1.isInNghieng() == run2.isInNghieng() &&
            run1.isGachChan() == run2.isGachChan() &&
            Objects.equals(Optional.ofNullable(run1.getMaMauChu()).orElse("000000"),
                    Optional.ofNullable(run2.getMaMauChu()).orElse("000000"));
    }
}
