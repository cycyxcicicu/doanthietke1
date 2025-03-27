package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test,String> {
 @Query("SELECT t FROM Test t WHERE t.isDelete = false or t.isDelete IS NULL ")
    List<Test> findAllActive();
@Query("SELECT t FROM Test t WHERE t.testId = :id AND (t.isDelete = false or t.isDelete IS NULL)")
Optional<Test> findActiveTestById(@Param("id") String id);

@Query("SELECT t FROM Test t WHERE t.classId.classId =:id AND ( t.isDelete = false or t.isDelete IS NULL) ")
List<Test> findAllActiveByClassId(@Param("id") String id);
}