package com.example.cauhoi2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.Category;
import com.example.cauhoi2.entity.Course;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    
}
