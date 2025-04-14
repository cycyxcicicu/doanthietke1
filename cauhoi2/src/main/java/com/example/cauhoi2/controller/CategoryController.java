package com.example.cauhoi2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.CategoryRequest;
import com.example.cauhoi2.dto.response.CategoryResponse;
import com.example.cauhoi2.dto.response.CourseResponse;
import com.example.cauhoi2.service.CategoryService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;
    @PostMapping("/create")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
return ApiResponse
            .<CategoryResponse>builder()
            .result(categoryService.create(request))
            .build();
    }
}
