package com.example.cauhoi2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.Clasz;

@Repository
public interface ClaszRepository extends JpaRepository<Clasz,String> {

    @Query("SELECT c FROM Clasz c WHERE c.classid IN :classIds")
    List<Clasz> findClassesByClassIds(@Param("classIds") List<String> classIds);

    
}
