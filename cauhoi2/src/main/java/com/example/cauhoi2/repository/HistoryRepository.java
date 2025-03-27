package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    
    @Query("SELECT c FROM History c WHERE c.userId =:userId AND c.testId =:testId")
    List<History> getHistoryUserTest(@Param("testId") String testId, @Param("userId") String userId);
    
    @Query("SELECT DISTINCT c.number FROM History c WHERE c.userId = :userId AND c.testId = :testId ORDER BY c.number DESC")
    List<String> findDistinctTestNumbers(@Param("userId") String userId, @Param("testId") String testId);

    @Query("SELECT h FROM History h WHERE h.question.questionsId = :questionsId")
    List<History> findByQuestionId(@Param("questionsId") String questionsId);
    
    @Query("SELECT h FROM History h WHERE h.question.questionsId IN :questionsIds")
    List<History> findByQuestionIds(@Param("questionsIds") List<String> questionsIds);
}
