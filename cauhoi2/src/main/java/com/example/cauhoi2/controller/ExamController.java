package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.response.ApiResponse;
import com.example.cauhoi2.dto.response.ExamResponse;
import com.example.cauhoi2.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    ExamService examService;
    @GetMapping("/{id}")
    public ApiResponse<ExamResponse> getExam(@PathVariable("id") String id) {
        return ApiResponse.<ExamResponse>builder().code(1000).result(examService.getById(id)).build();
    }
}
