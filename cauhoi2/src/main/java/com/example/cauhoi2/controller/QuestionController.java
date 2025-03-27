package com.example.cauhoi2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.service.QuestionService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/questions")

@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/{testid}")
    public ApiResponse<List<QuestionResponse>> getQuestions(@PathVariable("testid") String id) {
        ApiResponse<List<QuestionResponse>> response = new ApiResponse<>();
        response.setResult(questionService.getQuestion(id));
        return response;
    }
    
    

    
}
