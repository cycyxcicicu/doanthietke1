package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.response.ExamResponse;
import com.example.cauhoi2.entity.Exam;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.ExamMapper;
import com.example.cauhoi2.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    @Autowired
    ExamRepository examRepository;
    @Autowired
    ExamMapper examMapper;
    public ExamResponse getById(String id) {
        Exam exam = examRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.EXAM_NOT_EXISTED));
        return examMapper.toExamResponse(exam);
    }
}
