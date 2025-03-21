package com.example.cauhoi2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.Question;
import com.example.cauhoi2.entity.UserClassRole;

@Repository
public interface QuestionRepostiory extends JpaRepository<Question,String> {
List<Question> findByTest_Testid(String TestId);


}