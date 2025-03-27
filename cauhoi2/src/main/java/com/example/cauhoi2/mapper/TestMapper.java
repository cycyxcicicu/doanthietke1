package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.request.TestRequest;
import com.example.cauhoi2.dto.response.TestResponse;
import com.example.cauhoi2.entity.Test;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface TestMapper {
    Test toTest(TestRequest request);

    TestResponse toTestResponse(Test test);

    void toUpdateTest(@MappingTarget Test test, TestRequest request);
}
