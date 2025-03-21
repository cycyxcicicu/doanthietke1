package com.example.cauhoi2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cauhoi2.dto.request.QuestionRequest;
import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.entity.Question;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.QuestionMapper;
import com.example.cauhoi2.repository.QuestionRepostiory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionService {
    QuestionRepostiory questionRepostiory;
    QuestionMapper questionMapper;
    public QuestionResponse createQuestion(QuestionRequest request){
        Question question = questionMapper.toQuestion(request);
        
        questionRepostiory.save(question);

        return questionMapper.toQuestionResponse(question);
    }
    public QuestionResponse updateQuestion(QuestionRequest request, String id){

        Question question = questionRepostiory.findById(id).orElseThrow(()-> new AppException(ErrorCode.QUESTION_NOT_EXISTED));
        
        questionRepostiory.save(question);

        return questionMapper.toQuestionResponse(question);
    }
    public QuestionResponse deleteQuestion(String id){
        Question question = questionRepostiory.findById(id).orElseThrow(()-> new AppException(ErrorCode.QUESTION_NOT_EXISTED));
        question.setIsdelete(true);
        question=questionRepostiory.save(question);
        return questionMapper.toQuestionResponse(question);
    }
    
    public List<QuestionResponse> getQuestion(String testid){
        List<Question> question = questionRepostiory.findByTest_Testid(testid);

        return questionMapper.toQuestionResponses(question);
    }

}
