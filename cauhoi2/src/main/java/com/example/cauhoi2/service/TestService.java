package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.request.TestRequest;
import com.example.cauhoi2.dto.response.TestResponse;
import com.example.cauhoi2.entity.Clasz;
import com.example.cauhoi2.entity.History;
import com.example.cauhoi2.entity.Question;
import com.example.cauhoi2.entity.Test;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.TestMapper;
import com.example.cauhoi2.repository.HistoryRepository;
import com.example.cauhoi2.repository.QuestionRepository;
import com.example.cauhoi2.repository.TestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestService {
    TestRepository TestRepository;
    QuestionRepository QuestionRepository;
    TestMapper testMapper;
    HistoryRepository historyRepository;
    public TestResponse createTest(TestRequest request, String id) {
        Test test = testMapper.toTest(request);
        Clasz clasz = new Clasz();
        clasz.setClassId(id);
        test.setClassId(clasz);
        Test test1 = TestRepository.save(test);
        for (Question i : request.getQuestions()) {
            i.setTest(test1);
        }
        QuestionRepository.saveAll(request.getQuestions());
        return testMapper.toTestResponse(test);
    }

   public TestResponse updatetest(TestRequest request, String id) {
    // 1. Lấy đối tượng Test từ cơ sở dữ liệu
    Test test = TestRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TEST_NOT_EXISTED));

    // 2. Cập nhật các thuộc tính của Test từ request
    testMapper.toUpdateTest(test, request);

    // 3. Lấy danh sách các câu hỏi hiện tại từ cơ sở dữ liệu
    List<Question> questions = QuestionRepository.findByTestTestId(id);

    // 4. Phân loại câu hỏi từ request
    List<Question> newQuestions = request.getQuestions().stream()
            .filter(q -> q.getQuestionsId() == null) // Câu hỏi mới (không có ID)
            .toList();

    List<Question> existingQuestions = request.getQuestions().stream()
            .filter(q -> q.getQuestionsId() != null) // Câu hỏi đã có ID
            .toList();

    // 5. Kiểm tra câu hỏi cũ có tồn tại trong lịch sử hay không
    List<String> requestQuestionIds = existingQuestions.stream()
            .map(Question::getQuestionsId)
            .toList();

    // 6. Nếu có câu hỏi cũ bị xóa khỏi `questionsList`, xóa nó khỏi `history` trước
    List<Question> questionsToRemoveFromHistory = questions.stream()
            .filter(existingQuestion -> !requestQuestionIds.contains(existingQuestion.getQuestionsId()))
            .toList();

    if (!questionsToRemoveFromHistory.isEmpty()) {
        // Lấy danh sách `history` của những câu hỏi bị xóa
        List<History> historiesToRemove = historyRepository.findByQuestionIds(questionsToRemoveFromHistory.stream()
                .map(Question::getQuestionsId).collect(Collectors.toList()));

        // Xóa câu hỏi trong `history` trước khi xóa trong `questions`
        historyRepository.deleteAll(historiesToRemove);

        // Xóa các câu hỏi trong `questions`
        QuestionRepository.deleteAll(questionsToRemoveFromHistory);
    }

    // 7. Lưu các câu hỏi mới vào cơ sở dữ liệu
    for (Question question : newQuestions) {
        question.setTest(test); // Gắn Test cho câu hỏi mới
        QuestionRepository.save(question); // Lưu câu hỏi mới
    }

    // 8. Cập nhật các câu hỏi đã có trong cơ sở dữ liệu
    for (Question question : existingQuestions) {
        questions.stream()
                .filter(q -> q.getQuestionsId().equals(question.getQuestionsId()))
                .findFirst()
                .ifPresent(existingQuestion -> {
                    BeanUtils.copyProperties(question, existingQuestion, "questionsid", "test");
                    QuestionRepository.save(existingQuestion);
                });
    }

    // 9. Lưu đối tượng Test
    return testMapper.toTestResponse(TestRepository.save(test));
}

public String deleteTest(String id) {
    // 1. Kiểm tra xem Test có tồn tại không
    Test test = TestRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.TEST_NOT_EXISTED));

            test.setIsDelete(true);
    // 2. Lấy danh sách các câu hỏi liên quan (nếu cần xử lý thủ công)
    List<Question> questions = QuestionRepository.findByTestTestId(id);
    if (!questions.isEmpty()) {
        // Đánh dấu tất cả các câu hỏi liên quan đã bị xóa
        for (Question question : questions) {
            question.setIsDelete(true);
        }
        QuestionRepository.saveAll(questions);
    }
    

    // 3. Xóa tất cả các câu hỏi liên quan
    QuestionRepository.saveAll(questions);

    // 4. Xóa đối tượng Test
    TestRepository.save(test);

    return " xóa thành công";
}
public List<TestResponse> getTestById(String id) {
    // Tìm Test theo id và kiểm tra trạng thái isDeleted
    List<Test> test = TestRepository.findAllActiveByClassId(id); // Kiểm tra isDeleted = false
            
    // Trả về TestResponse sau khi ánh xạ
    return test.stream()
            .map(testMapper::toTestResponse)
            .toList();
}
public List<TestResponse> getAllTests() {
    // Truy vấn tất cả các Test chưa bị xóa
    List<Test> tests = TestRepository.findAllActive();

    // Chuyển đổi sang danh sách TestResponse
    return tests.stream()
    .filter(test -> Boolean.TRUE.equals(test.getTrangThai())) // Lọc trạng thái true
    .map(testMapper::toTestResponse)
    .toList();
}



}
