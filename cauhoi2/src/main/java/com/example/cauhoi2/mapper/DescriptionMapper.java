package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.file_data.DescriptionQuestionResponse;
import com.example.cauhoi2.entity.file_data.DescriptionQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DescriptionMapper {
    DescriptionQuestionResponse toDescriptionQuestionResponse(DescriptionQuestion descriptionQuestion);
}
