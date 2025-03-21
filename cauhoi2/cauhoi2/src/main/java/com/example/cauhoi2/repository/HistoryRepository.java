package com.example.cauhoi2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    
    @Query("SELECT c FROM History c WHERE c.userid =:userid AND c.testid =:testid")
    List<History> gethistory_user_test(@Param("testid") String testid, @Param("userid") String userid);
    
    @Query("SELECT DISTINCT c.number FROM History c WHERE c.userid = :userid AND c.testid = :testid ORDER BY c.number DESC")
    List<String> findDistinctTestNumbers(@Param("userid") String userid, @Param("testid") String testid);

    @Query("SELECT h FROM History h WHERE h.question.questionsid = :questionsid")
    List<History> findByQuestionId(@Param("questionsid") String questionsid);
    
    @Query("SELECT h FROM History h WHERE h.question.questionsid IN :questionsIds")
    List<History> findByQuestionIds(@Param("questionsIds") List<String> questionsIds);
}
