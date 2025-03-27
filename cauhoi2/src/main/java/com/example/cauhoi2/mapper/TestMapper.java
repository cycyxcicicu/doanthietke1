package com.example.cauhoi2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.cauhoi2.dto.request.TestRequest;
import com.example.cauhoi2.dto.response.TestResponse;
import com.example.cauhoi2.entity.Test;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface TestMapper {
    Test toTest(TestRequest request);

    TestResponse toTestResponse(Test test);

    void toUpdateTest(@MappingTarget Test test, TestRequest request);
}
