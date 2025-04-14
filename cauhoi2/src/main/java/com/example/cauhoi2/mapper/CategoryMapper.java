package com.example.cauhoi2.mapper;

import java.net.CacheRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.cauhoi2.dto.response.CategoryResponse;
import com.example.cauhoi2.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    CategoryResponse toCategoryResponse(Category category);
}
