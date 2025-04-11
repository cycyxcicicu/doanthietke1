package com.example.cauhoi2.controller;


import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.CourseCreateRequest;
import com.example.cauhoi2.dto.response.CourseResponse;
import com.example.cauhoi2.service.CourseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CourseController {
    CourseService courseService;
    @GetMapping
    public ApiResponse<List<CourseResponse>> getCourses() {
        ApiResponse<List<CourseResponse>> response = new ApiResponse<>();
        response.setResult(courseService.getCourses());
        return response;
    }
    @GetMapping("/{course_id}")
    public ApiResponse<CourseResponse> getCourse(@PathVariable("course_id") String id) {
        ApiResponse<CourseResponse> response = new ApiResponse<>();
        response.setResult(courseService.getCourse(id));
        return response;
    }
    @PostMapping("/create")
    public ApiResponse<CourseResponse> createCourse(@RequestBody @Valid CourseCreateRequest request) {
        return ApiResponse
            .<CourseResponse>builder()
            .result(courseService.create(request))
            .build();
    }
    @PutMapping("/edit")
    public ApiResponse<?> editSource() {
        return ApiResponse
            .builder().build();
    }
}
