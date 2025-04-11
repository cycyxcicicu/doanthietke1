package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.request.QuestionRequest;
import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question toQuestion(QuestionRequest request);
    void toUpdateQuestion(@MappingTarget Question question, QuestionRequest request);
    @Mapping(target = "categoryId", source = "question.category.id")
    QuestionResponse toQuestionResponse(Question question);
    List<QuestionResponse> toQuestionResponses(List<Question> questions);
}