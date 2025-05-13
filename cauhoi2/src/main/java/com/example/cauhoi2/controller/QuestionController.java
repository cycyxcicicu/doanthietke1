package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.response.ApiResponse;
import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.service.QuestionService;
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
    public ApiResponse<?> createQuestion() {
        return ApiResponse.builder().build();
    }
}
