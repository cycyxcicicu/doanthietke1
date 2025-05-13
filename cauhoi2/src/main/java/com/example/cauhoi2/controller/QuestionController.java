package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.QuestionRequest;
import com.example.cauhoi2.dto.request.QuestionUpdateRequest;

import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.service.QuestionService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/questions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/{course_id}")
    public ApiResponse<List<QuestionResponse>> getQuestions(@PathVariable("course_id") String id) {
        ApiResponse<List<QuestionResponse>> response = new ApiResponse<>();
        response.setResult(questionService.getQuestionsByCourse(id));
        return response;
    }
    @PostMapping("/create")
    public ApiResponse<List<QuestionResponse>> createQuestion(@RequestBody @Valid List<QuestionRequest> request) {
        ApiResponse<List<QuestionResponse>> apiResponse = new ApiResponse<>();
        List<QuestionResponse> questionResponses= questionService.createQuestions(request);
        apiResponse.setResult(questionResponses);
        apiResponse.setMessage("tạo thành công câu hỏi");
        apiResponse.setCode(1000);
        return apiResponse;
    }
    @PutMapping("/update")
    public ApiResponse<List<QuestionResponse>> updateQuestion(@RequestBody @Valid List<QuestionUpdateRequest> request) {
        ApiResponse<List<QuestionResponse>> apiResponse = new ApiResponse<>();
        
        List<QuestionResponse> questionResponses= questionService.updateRequests(request);
        apiResponse.setResult(questionResponses);
        apiResponse.setMessage("update thành công câu hỏi");
        apiResponse.setCode(1000);
        return apiResponse;
    }
    
}
