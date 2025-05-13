package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,String> {
    @Query("SELECT q FROM Question q WHERE q.category.id = :category_id AND q.isDeleted = false")
    List<Question> findByCategoryId(@Param("category_id") String categoryId);
    @Query("SELECT q FROM Question q WHERE q.category.course.id = :course_id AND q.isDeleted = false ORDER BY q.category.name")
    List<Question> findByCourseId(@Param("course_id") String courseId);
}