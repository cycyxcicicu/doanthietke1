package com.example.cauhoi2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.CategoryRequest;
import com.example.cauhoi2.dto.request.CategoryUpdateRequest;
import com.example.cauhoi2.dto.response.CategoryResponse;
import com.example.cauhoi2.dto.response.CourseResponse;
import com.example.cauhoi2.service.CategoryService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
    @PutMapping("update")
    public ApiResponse<CategoryResponse> update(@RequestBody @Valid CategoryUpdateRequest request) {
        return ApiResponse
        .<CategoryResponse>builder()
        .result(categoryService.update(request))
        .build();
    }
    @PutMapping("delete")
    public ApiResponse<CategoryResponse> delete(@RequestBody @Valid CategoryUpdateRequest request) {
        return ApiResponse
        .<CategoryResponse>builder()
        .result(categoryService.delete(request))
        .build();
    }
    @GetMapping()
    public  ApiResponse<List<CategoryResponse>> getMethodName() {
        return ApiResponse
        .<List<CategoryResponse>>builder()
        .result(categoryService.getCategories())
        .build();
    }
    
}
