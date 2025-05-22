package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.DescriptionResponse;
import com.example.cauhoi2.entity.Description;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DescriptionMapper {
    DescriptionResponse toDescriptionQuestionResponse(Description descriptionQuestion);
}
