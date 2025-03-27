package com.example.cauhoi2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,String> {
List<Question> findByTestTestId(String TestId);


}