package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.UserClassRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserClassRoleRepository extends JpaRepository<UserClassRole,String> {
    @Query("SELECT DISTINCT u FROM UserClassRole u WHERE u.clasz.classId = :class_id")
    List<UserClassRole> findByClassId(@Param(("class_id")) String class_id);
    @Query("SELECT DISTINCT u FROM UserClassRole u WHERE u.user.userId = :user_id")
    List<UserClassRole> findByUserId (@Param("user_id") String user_id);

    @Query("SELECT DISTINCT u.clasz.classId  FROM UserClassRole u WHERE u.user.userId = :id AND (u.isDelete = false or u.isDelete IS NULL )")
    List<String> findByIdAndDeleteNotTrue(@Param("id") String id);

    @Query("SELECT DISTINCT u FROM UserClassRole u WHERE u.user.userId = :userId AND u.clasz.classId = :classId")
    Optional<UserClassRole> findByClassAndUser(@Param("classId") String classId, @Param("userId") String userId);

    @Query("SELECT DISTINCT u.user.userId  FROM UserClassRole u WHERE u.clasz.classId = :classId AND (u.isDelete = false or u.isDelete IS NULL )  AND u.role ='STUDENT' ")
    List<String> findByUserStudentAndNotDelete(@Param("classId") String classId);

}