package com.example.cauhoi2.service;

import com.example.cauhoi2.client.ImageUploadClient;
import com.example.cauhoi2.dto.request.QuestionRequest;
import com.example.cauhoi2.dto.request.QuestionUpdateRequest;
import com.example.cauhoi2.dto.response.ImageResponse;
import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.entity.Category;
import com.example.cauhoi2.entity.LevelQuestion;
import com.example.cauhoi2.entity.Question;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.QuestionMapper;
import com.example.cauhoi2.repository.CategoryRepository;
import com.example.cauhoi2.repository.QuestionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionService {
    
    QuestionRepository QuestionRepository;
    QuestionMapper questionMapper;
    CategoryRepository categoryRepository;

    public QuestionResponse createQuestion(QuestionRequest request){
        Question question = questionMapper.toQuestion(request);
        QuestionRepository.save(question);
        return questionMapper.toQuestionResponse(question);
    }

    public List<QuestionResponse> createQuestions(List<QuestionRequest> requests) {
        List<Question> questions = requests.stream()
            .map(request -> {
                // Map phần còn lại bằng mapper
                Question question = questionMapper.toQuestion(request);
                
                // Lấy category từ DB dựa trên categoryId từ request
                Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_EXISTED));
    
                question.setCategory(category);
                
                return question;
            })
            .collect(Collectors.toList());
    
        List<Question> saved = QuestionRepository.saveAll(questions);
    
        return saved.stream()
            .map(questionMapper::toQuestionResponse)
            .collect(Collectors.toList());
    }
public List<QuestionResponse> updateRequests(List<QuestionUpdateRequest> requests) {
    List<Question> updatedQuestions = requests.stream()
        .map(request -> {
            // Tìm question theo ID
            Question existing = QuestionRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_EXISTED));

            // Cập nhật dữ liệu từ request sang entity cũ
            existing.setContent(request.getContent());
            existing.setAnswers(request.getAnswers());
            existing.setChoices(request.getChoices());
            
            existing.setLevel(request.getLevel());

            // Gán category mới (nếu cần)
            Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_EXISTED));
            existing.setCategory(category);

            return existing;
        })
        .collect(Collectors.toList());

    // Lưu danh sách đã cập nhật
    List<Question> saved = QuestionRepository.saveAll(updatedQuestions);

    // Trả về response
    return saved.stream()
        .map(questionMapper::toQuestionResponse)
        .collect(Collectors.toList());
}

    public QuestionResponse updateQuestion(QuestionRequest request, String id){
        Question question = QuestionRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.QUESTION_NOT_EXISTED));
        QuestionRepository.save(question);
        return questionMapper.toQuestionResponse(question);
    }
    public QuestionResponse deleteQuestion(String id){
        Question question = QuestionRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.QUESTION_NOT_EXISTED));
        question.setIsDeleted(true);
        question=QuestionRepository.save(question);
        return questionMapper.toQuestionResponse(question);
    }
    
    public List<QuestionResponse> getQuestionsByCourse(String categoryId){
        List<Question> question = QuestionRepository.findByCategoryId(categoryId);

        return questionMapper.toQuestionResponses(question);
    }
}
