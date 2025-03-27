package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,String> {
List<Question> findByTestTestId(String TestId);


}