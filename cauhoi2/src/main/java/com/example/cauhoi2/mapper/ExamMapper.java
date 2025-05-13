package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.file_data.ExamResponse;
import com.example.cauhoi2.entity.file_data.Exam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    ExamResponse toExamResponse(Exam exam);
}
