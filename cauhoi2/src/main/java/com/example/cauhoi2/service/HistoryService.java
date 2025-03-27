package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.request.HistoryRequest;
import com.example.cauhoi2.dto.response.HistoryResponse;
import com.example.cauhoi2.entity.History;
import com.example.cauhoi2.entity.Question;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.HistoryMapper;
import com.example.cauhoi2.repository.HistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryService {
        HistoryRepository historyRepository;
        HistoryMapper historyMapper;
public List<HistoryResponse> createHistory(List<HistoryRequest> request) {
   // Lấy thông tin userId và testId từ phần tử đầu tiên của request
   String userId = request.get(0).getUserId();
   String testId = request.get(0).getTestId();

   // Kiểm tra userId và testId có hợp lệ không
   if (userId == null || testId == null) {
       throw new AppException(ErrorCode.NOT_USERID_TESTID);
   }

   // Lấy danh sách số lần làm bài (number) từ cơ sở dữ liệu cho userId và testId
   List<String> testNumbers = historyRepository.findDistinctTestNumbers(userId, testId);

   // Tính số lần làm bài hiện tại (currentTestNumber)
   int currentTestNumber = 1;

   if (!testNumbers.isEmpty()) {
       try {
           // Chuyển các giá trị trong danh sách từ String sang Integer và tìm giá trị lớn nhất
           List<Integer> numbers = testNumbers.stream()
                   .map(Integer::parseInt) // Chuyển đổi từ String thành Integer
                   .collect(Collectors.toList());

           // Lấy giá trị lớn nhất trong danh sách
           Integer maxTestNumber = Collections.max(numbers);

           // Cộng thêm 1 để có số lần làm bài tiếp theo
           currentTestNumber = maxTestNumber + 1;

       } catch (NumberFormatException e) {
           // Xử lý trường hợp không thể chuyển đổi số (nếu có)
           throw new AppException(ErrorCode.INVALID_NUMBER_FORMAT);
       }
   }

   // Danh sách lưu các HistoryResponse
   List<HistoryResponse> historyReponses = new ArrayList<>();

   // Duyệt qua từng phần tử trong request để xử lý
   for (HistoryRequest historyRequest : request) {
       // Chuyển đổi HistoryRequest thành History
       History history = historyMapper.toHistory(historyRequest);

       // Tạo đối tượng Question từ questionid của HistoryRequest
       String questionId = historyRequest.getQuestionId();
       if (questionId == null) {
           throw new AppException(ErrorCode.NOT_USERID_TESTID);
       }

       Question question = new Question();
       question.setQuestionsId(questionId);

       // Gán số lần làm bài (number) vào History
       history.setNumber(Integer.toString(currentTestNumber));

       // Gán Question vào History
       history.setQuestion(question);

       // Lưu History vào repository
       historyRepository.save(history);

       // Chuyển History thành HistoryReponse
       HistoryResponse historyReponse = historyMapper.toHistoryReponse(history);
       historyReponse.setQuestionId(questionId);

       // Thêm vào danh sách kết quả
       historyReponses.add(historyReponse);
   }

   // Trả về danh sách các HistoryReponse
   return historyReponses;
}
public List<HistoryResponse> gethistory(String testid, String userid){
    
    List<History> histories = historyRepository.getHistoryUserTest(testid, userid);
    List<HistoryResponse> historyReponses = new ArrayList<>();
    for ( History history : histories){
        HistoryResponse historyReponse = new HistoryResponse();

        String questionId = history.getQuestion().getQuestionsId();
        historyReponse =historyMapper.toHistoryReponse(history);
        historyReponse.setQuestionId(questionId);

        historyReponses.add(historyReponse);
    }
    return historyReponses;
}

    
}
