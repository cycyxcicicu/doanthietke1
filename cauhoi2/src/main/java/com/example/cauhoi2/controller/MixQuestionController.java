package com.example.cauhoi2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.service.MixQuestionService;
import com.example.cauhoi2.service.MixQuestionService1;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/file")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MixQuestionController {
    MixQuestionService mixQuestionService;
    MixQuestionService1 mixQuestionService1;
    @PostMapping("upload")
    public ApiResponse<Object> upload(@RequestParam("file") MultipartFile file) throws IOException{
        return ApiResponse
            .builder()
            .result(mixQuestionService.upload(file))
            .build();
    }
     @PostMapping("upload1")
    public ApiResponse<Object> upload1(@RequestParam("file") MultipartFile file) throws IOException{
        return ApiResponse
            .builder()
            .result(mixQuestionService1.extract(file))
            .build();
    }
    
    
}
