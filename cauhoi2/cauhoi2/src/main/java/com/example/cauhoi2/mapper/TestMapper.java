package com.example.cauhoi2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.cauhoi2.dto.request.ClassUpdateRequest;
import com.example.cauhoi2.dto.request.TestRequest;
import com.example.cauhoi2.dto.response.TestResponse;
import com.example.cauhoi2.entity.Clasz;
import com.example.cauhoi2.entity.Test;

@Mapper(componentModel = "spring")
public interface TestMapper {
    Test toTest(TestRequest request);
    
    @Mapping(source = "questions", target = "questions")
    TestResponse toTestResponse(Test test);

    @Mapping(target = "testid", ignore = true)
    void toupdatetest(@MappingTarget Test test, TestRequest request);
}
