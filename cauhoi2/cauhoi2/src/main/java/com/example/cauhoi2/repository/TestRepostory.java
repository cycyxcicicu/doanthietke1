package com.example.cauhoi2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.Question;
import com.example.cauhoi2.entity.Test;
import com.example.cauhoi2.entity.UserClassRole;

@Repository
public interface TestRepostory extends JpaRepository<Test,String> {
 @Query("SELECT t FROM Test t WHERE t.isdelete = false or t.isdelete IS NULL ") 
    List<Test> findAllActive();
@Query("SELECT t FROM Test t WHERE t.testid = :id AND (t.isdelete = false or t.isdelete IS NULL)")
Optional<Test> findActiveTestById(@Param("id") String id);

@Query("SELECT t FROM Test t WHERE t.classid.classid =:id AND ( t.isdelete = false or t.isdelete IS NULL) ")
List<Test> findAllActiveByClassid(@Param("id") String id);
}