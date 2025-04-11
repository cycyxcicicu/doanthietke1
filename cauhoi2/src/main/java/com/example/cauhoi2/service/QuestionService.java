package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.request.QuestionRequest;
import com.example.cauhoi2.dto.response.QuestionResponse;
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

import java.util.List;


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
