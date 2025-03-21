package com.example.cauhoi2.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.cauhoi2.dto.request.QuestionRequest;
import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toQuestion(QuestionRequest request);

    @Mapping(target = "questionsid", ignore = true)
    void toupdateQuestion(@MappingTarget Question question, QuestionRequest request);

    QuestionResponse toQuestionResponse(Question question);
    List<QuestionResponse> toQuestionResponses(List<Question> questions);
}
