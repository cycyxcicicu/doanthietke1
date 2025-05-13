package com.example.cauhoi2.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.CategoryRequest;
import com.example.cauhoi2.dto.request.CategoryUpdateRequest;
import com.example.cauhoi2.dto.response.CategoryResponse;
import com.example.cauhoi2.entity.Category;
import com.example.cauhoi2.entity.Course;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.CategoryMapper;
import com.example.cauhoi2.repository.CategoryRepository;
import com.example.cauhoi2.repository.CourseRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
        CourseRepository courseRepository;
        CategoryRepository categoryRepository;
        CategoryMapper categoryMapper;
        public CategoryResponse create (CategoryRequest request){
            Category category = new Category();
            category.setName(request.getName());
            Course Course = courseRepository.findById(request.getCourseid()).orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            category.setCourse(Course);
            category = categoryRepository.save(category);
            return categoryMapper.toCategoryResponse(category);
        }
        public CategoryResponse update(CategoryUpdateRequest request) {

            Category category = categoryRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setName(request.getName());
            Course Course = courseRepository.findById(request.getCourseid()).orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            category.setCourse(Course);
            category = categoryRepository.save(category);
            return categoryMapper.toCategoryResponse(category);
        }
        public CategoryResponse delete(CategoryUpdateRequest request) {
            Category category = categoryRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setIsDeleted(true);
            category = categoryRepository.save(category);
            return categoryMapper.toCategoryResponse(category);
        }
        public List<CategoryResponse> getCategories() {
            return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
        }

}
