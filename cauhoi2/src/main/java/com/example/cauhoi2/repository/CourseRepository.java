package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query("SELECT c FROM Course c WHERE c.isDeleted != TRUE AND c.user.id = :user_id")
    Optional<Course> findAllByUserId(@Param("user_id") String userId);
    @Query("SELECT c FROM Course c WHERE c.isDeleted != TRUE AND c.id = :course_id")
    Optional<Course> findById(@Param("course_id") String id);
}
