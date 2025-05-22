package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.ExamResponse;
import com.example.cauhoi2.dto.response.GroupResponse;
import com.example.cauhoi2.entity.Exam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    ExamResponse toExamResponse(Exam exam);

    ExamResponse cloneExam(ExamResponse group);
}
