package com.example.cauhoi2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MixQuestionService {

    private final ImageService imageService;

    public record QuestionGroupDTO(
            String groupTag,
            List<QuestionDTO> questions) {
    }

    public record QuestionDTO(
            String questionText,
            List<String> options,
            List<String> correctAnswer,
            List<Boolean> fixedOptions,
            List<String> imageLinks,
            boolean importantQuestion) {
    }

    public List<QuestionGroupDTO> upload(MultipartFile file) throws IOException {
        boolean importantQuestion = false;
        List<QuestionGroupDTO> result = new ArrayList<>();
        List<QuestionDTO> currentGroupQuestions = new ArrayList<>();
        String currentGroup = "<g0>"; // mặc định ban đầu

        String currentQuestionText = null;
        List<String> options = new ArrayList<>();
        List<String> correctAnswer = new ArrayList<>();
        List<Boolean> fixedOptions = new ArrayList<>();
        List<String> imageLinks = new ArrayList<>();
        List<XWPFPicture> pendingPictures = new ArrayList<>();

        try (XWPFDocument doc = new XWPFDocument(file.getInputStream())) {
            for (XWPFParagraph para : doc.getParagraphs()) {
                String line = para.getText().trim();
                for (XWPFRun run : para.getRuns()) {
                    List<XWPFPicture> pics = run.getEmbeddedPictures();
                    if (pics != null && !pics.isEmpty()) {
                        pendingPictures.addAll(pics);
                    }
                }

                if (line.isEmpty())
                    continue;

                if (line.matches("<g[0-3]>")) {
                    if (currentQuestionText != null && !options.isEmpty()) {
                        currentGroupQuestions.add(new QuestionDTO(
                                currentQuestionText,
                                options,
                                correctAnswer,
                                fixedOptions,
                                imageLinks,
                                importantQuestion));
                        currentQuestionText = null;
                    }

                    // ✅ Chỉ add nhóm nếu có câu hỏi
                    if (!currentGroupQuestions.isEmpty()) {
                        result.add(new QuestionGroupDTO(currentGroup, new ArrayList<>(currentGroupQuestions)));
                        currentGroupQuestions.clear();
                    }

                    // reset cho nhóm mới
                    currentGroup = line;
                    options = new ArrayList<>();
                    correctAnswer = new ArrayList<>();
                    fixedOptions = new ArrayList<>();
                    imageLinks = new ArrayList<>();
                    pendingPictures.clear();
                    importantQuestion = false;
                    continue;
                }

                if (line.toLowerCase().matches("^(#)?(câu|question)\\s*\\d+.*")) {
                    if (currentQuestionText != null && !options.isEmpty()) {
                        currentGroupQuestions.add(new QuestionDTO(
                                currentQuestionText,
                                options,
                                correctAnswer,
                                fixedOptions,
                                imageLinks,
                                importantQuestion));

                    }
                    importantQuestion = line.trim().startsWith("#");
                    currentQuestionText = line.replaceFirst("^#", "").trim();

                    options = new ArrayList<>();
                    correctAnswer = new ArrayList<>();
                    fixedOptions = new ArrayList<>();
                    imageLinks = new ArrayList<>();
                    // for (XWPFPicture pic : pendingPictures) {

                    // byte[] imageBytes = pic.getPictureData().getData();
                    // String ext = pic.getPictureData().suggestFileExtension();
                    // String fileName = "image." + ext;
                    // MultipartFile multipartFile = new MockMultipartFile(fileName, fileName,
                    // "image/" + ext,
                    // imageBytes);

                    // ImageResponse res = imageService.saveImage(multipartFile);
                    // if (res != null && res.getData() != null) {
                    // imageLinks.add(res.getData().getLink());
                    // System.out.println("✅ Uploaded image: " + res.getData().getLink());
                    // } else {
                    // System.err.println("❌ Upload failed");
                    // }
                    // }
                    // pendingPictures.clear(); // ảnh đã gán xong
                    // continue;
                }
                // Nếu có ảnh trước đó → xử lý upload

                if (line.matches("^#?[A-Da-d][\\.|\\)]\\s.*")) {
                    // Tách options
                    List<String> lineOptions = new ArrayList<>();
                    Matcher matcher = Pattern.compile("((#?[A-Da-d][\\.|\\)])\\s.*?)(?=\\s#?[A-Da-d][\\.|\\)]|$)").matcher(line);
                    List<Integer> positions = new ArrayList<>();

                    while (matcher.find()) {
                        positions.add(matcher.start());
                    }

                    for (int j = 0; j < positions.size(); j++) {
                        int start = positions.get(j);
                        int end = (j + 1 < positions.size()) ? positions.get(j + 1) : line.length();
                        String option = line.substring(start, end).trim();
                        lineOptions.add(option);
                    }

                    // ✅ Quét runs để gom các từ gạch chân
                    Set<String> underlinedTexts = new HashSet<>();
                    for (XWPFRun run : para.getRuns()) {
                        if (run.getUnderline() != UnderlinePatterns.NONE && run.getText(0) != null) {
                            underlinedTexts.add(run.getText(0).trim());
                        }
                    }

                    // Xử lý từng option
                    for (String opt : lineOptions) {
                        boolean fixed = false;
                        String cleanOpt = opt;
                        // Nếu option bắt đầu bằng dấu #
                        if (cleanOpt.startsWith("#")) {
                            fixed = true;
                            cleanOpt = cleanOpt.substring(1).trim(); // Xóa dấu #
                        }

                        options.add(cleanOpt);
                        fixedOptions.add(fixed);
                        // Ngoài ra nếu option chứa từ bị gạch chân --> cũng là đáp án
                        for (String underlined : underlinedTexts) {
                            if (!underlined.isEmpty() && cleanOpt.contains(underlined)) {
                                if (!correctAnswer.contains(cleanOpt)) {
                                    correctAnswer.add(cleanOpt);
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if (currentQuestionText != null && !options.isEmpty()) {
                currentGroupQuestions.add(new QuestionDTO(
                        currentQuestionText,
                        options,
                        correctAnswer,
                        fixedOptions,
                        imageLinks,
                        importantQuestion));

            }
        }
        if (!currentGroupQuestions.isEmpty()) {
            result.add(new QuestionGroupDTO(currentGroup, currentGroupQuestions));
        }

        return shuffleByGroup(result);
    }

    public List<QuestionGroupDTO> shuffleByGroup(List<QuestionGroupDTO> groups) {
        List<QuestionGroupDTO> result = new ArrayList<>();
    
        for (QuestionGroupDTO group : groups) {
            String tag = group.groupTag();
            List<QuestionDTO> original = group.questions();
            List<QuestionDTO> processed = new ArrayList<>();
    
            switch (tag) {
                case "<g0>":
                    // Không xáo trộn gì
                    processed = original;
                    break;
    
                case "<g1>":
                    // Xáo trộn thứ tự câu hỏi
                    processed = new ArrayList<>(original);
                    Collections.shuffle(processed);
                    break;
    
                case "<g2>":
                    // Xáo trộn đáp án mỗi câu
                    for (QuestionDTO q : original) {
                        processed.add(shuffleAnswers(q));
                    }
                    break;
    
                case "<g3>":
                    // Xáo trộn cả câu hỏi lẫn đáp án
                    List<QuestionDTO> shuffled = new ArrayList<>(original);
                    Collections.shuffle(shuffled);
                    for (QuestionDTO q : shuffled) {
                        processed.add(shuffleAnswers(q));
                    }
                    break;
    
                default:
                    processed = original;
            }

            // 🔁 Sau khi xử lý xong thì đánh lại số thứ tự câu hỏi
        List<QuestionDTO> reNumbered = new ArrayList<>();
        int index = 1;
        for (QuestionDTO q : processed) {
            String newText = q.questionText().replaceFirst("^Câu\\s*\\d+", "Câu " + index);
            reNumbered.add(new QuestionDTO(
                    newText,
                    q.options(),
                    q.correctAnswer(),
                    q.fixedOptions(),
                    q.imageLinks(),
                    q.importantQuestion()
            ));
            index++;
        }
    
            result.add(new QuestionGroupDTO(tag, reNumbered));
        }
    
        return result;
    }
    

    private QuestionDTO shuffleAnswers(QuestionDTO q) {
        List<String> options = new ArrayList<>(q.options());
        List<Boolean> fixed = new ArrayList<>(q.fixedOptions());
    
        // Bước 1: chỉ shuffle những đáp án không bị fixed
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < fixed.size(); i++) {
            if (!fixed.get(i)) {
                indices.add(i);
            }
        }
    
        // Bước 2: tạo bản sao phần cần shuffle (bỏ prefix A., A) nếu có)
        List<String> subOptions = new ArrayList<>();
        for (int i : indices) {
            String content = options.get(i).replaceFirst("(?i)^[A-D][\\.|\\)]\\s*", "").trim(); // gỡ A. hoặc A)
            subOptions.add(content);
        }
    
        Collections.shuffle(subOptions);
    
        // Bước 3: Áp lại vào vị trí cũ (chưa gán lại A., B.)
        for (int i = 0; i < indices.size(); i++) {
            options.set(indices.get(i), subOptions.get(i));
        }
    
        // Bước 4: Gán lại nhãn A., B., C., ...
        List<String> relabeledOptions = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            char label = (char) ('A' + i);
            String content = options.get(i).replaceFirst("(?i)^[A-D][\\.|\\)]\\s*", "").trim(); // lại gỡ nhãn cũ nếu có
            relabeledOptions.add(label + ". " + content);
        }
    
        // Bước 5: Cập nhật lại đáp án đúng dựa theo nội dung
        List<String> newCorrect = new ArrayList<>();
        for (String correct : q.correctAnswer()) {
            String correctContent = correct.replaceFirst("(?i)^[A-D][\\.|\\)]\\s*", "").trim();
            for (String opt : relabeledOptions) {
                String optContent = opt.replaceFirst("(?i)^[A-D][\\.|\\)]\\s*", "").trim();
                if (correctContent.equals(optContent)) {
                    newCorrect.add(opt);
                    break;
                }
            }
        }
    
        return new QuestionDTO(
                q.questionText(),
                relabeledOptions,
                newCorrect,
                fixed,
                q.imageLinks(),
                q.importantQuestion()
        );
    }
    

}
