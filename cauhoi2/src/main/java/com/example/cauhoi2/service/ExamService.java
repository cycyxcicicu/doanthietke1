package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.response.file_data.ExamResponse;
import com.example.cauhoi2.entity.file_data.Exam;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.ExamMapper;
import com.example.cauhoi2.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (exam.getUser() != null && exam.getUser().getUsername().equals(username))
            throw new AppException(ErrorCode.UNAUTHORIZED);
        return examMapper.toExamResponse(exam);
    }
}
