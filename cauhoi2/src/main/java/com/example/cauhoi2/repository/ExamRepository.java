package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.file_data.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {
}
