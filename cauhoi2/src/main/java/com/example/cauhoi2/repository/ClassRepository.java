package com.example.cauhoi2.repository;

import java.util.List;

import com.example.cauhoi2.entity.Clasz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Clasz,String> {

    @Query("SELECT c FROM `class` c WHERE c.classId IN :classIds")
    List<Clasz> findClassesByClassIds(@Param("classIds") List<String> classIds);
    
}
