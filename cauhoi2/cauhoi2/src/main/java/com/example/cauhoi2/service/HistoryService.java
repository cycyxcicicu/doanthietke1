package com.example.cauhoi2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.cauhoi2.dto.request.HistoryRequest;
import com.example.cauhoi2.dto.response.HistoryReponse;
import com.example.cauhoi2.entity.History;
import com.example.cauhoi2.entity.Question;
import com.example.cauhoi2.entity.Test;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.HistoryMapper;
import com.example.cauhoi2.repository.HistoryRepository;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryService {
        HistoryRepository historyRepository;
        HistoryMapper historyMapper;
public List<HistoryReponse> createHistory(List<HistoryRequest> request) {
   // Lấy thông tin userId và testId từ phần tử đầu tiên của request
   String userId = request.get(0).getUserid();
   String testId = request.get(0).getTestid();

   // Kiểm tra userId và testId có hợp lệ không
   if (userId == null || testId == null) {
       throw new AppException(ErrorCode.NOT_USERID_TESTID);
   }

   // Lấy danh sách số lần làm bài (number) từ cơ sở dữ liệu cho userId và testId
   List<String> testNumbers = historyRepository.findDistinctTestNumbers(userId, testId);

   // Tính số lần làm bài hiện tại (currentTestNumber)
   Integer currentTestNumber = 1;

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
   List<HistoryReponse> historyReponses = new ArrayList<>();

   // Duyệt qua từng phần tử trong request để xử lý
   for (HistoryRequest historyRequest : request) {
       // Chuyển đổi HistoryRequest thành History
       History history = historyMapper.toHistory(historyRequest);

       // Tạo đối tượng Question từ questionid của HistoryRequest
       String questionId = historyRequest.getQuestionid();
       if (questionId == null) {
           throw new AppException(ErrorCode.NOT_USERID_TESTID);
       }

       Question question = new Question();
       question.setQuestionsid(questionId);

       // Gán số lần làm bài (number) vào History
       history.setNumber(currentTestNumber.toString());

       // Gán Question vào History
       history.setQuestion(question);

       // Lưu History vào repository
       historyRepository.save(history);

       // Chuyển History thành HistoryReponse
       HistoryReponse historyReponse = historyMapper.toHistoryReponse(history);
       historyReponse.setQuestionid(questionId);

       // Thêm vào danh sách kết quả
       historyReponses.add(historyReponse);
   }

   // Trả về danh sách các HistoryReponse
   return historyReponses;
}
public List<HistoryReponse> gethistory( String testid,String userid){
    
    List<History> histories = historyRepository.gethistory_user_test(testid, userid);
    List<HistoryReponse> historyReponses = new ArrayList<>();
    for ( History history : histories){
        HistoryReponse historyReponse = new HistoryReponse();

        String questionId = history.getQuestion().getQuestionsid();
        historyReponse =historyMapper.toHistoryReponse(history);
        historyReponse.setQuestionid(questionId);

        historyReponses.add(historyReponse);
    }
    return historyReponses;
}

    
}
