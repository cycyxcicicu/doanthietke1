package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.file_data.QuestionResponse;
import com.example.cauhoi2.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionFileMapper {
    QuestionResponse toQuestionResponse(Question question);
}
