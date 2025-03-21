package com.example.cauhoi2.repository;

import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.UserClassRole;

@Repository
public interface UserClassRoleRepostiory extends JpaRepository<UserClassRole,String> {
    List<UserClassRole> findByClasz_Classid(String class_id);
    List<UserClassRole> findByUser_Userid (String user_id);

    @Query("SELECT DISTINCT u.clasz.classid  FROM UserClassRole u WHERE u.user.userid = :id AND (u.isdelete = false or u.isdelete IS NULL )")
    List<String> findByIdAndDeleteNotTrue(@Param("id") String id);

    @Query("SELECT DISTINCT u FROM UserClassRole u WHERE u.user.userid = :usetid AND u.clasz.classid = :classid")
    Optional<UserClassRole> findByClassAndUser(@Param("classid") String classid, @Param("usetid") String usetid);

    @Query("SELECT DISTINCT u.user.userid  FROM UserClassRole u WHERE u.clasz.classid = :classid AND (u.isdelete = false or u.isdelete IS NULL )  AND u.role ='STUDENT' ")
    List<String> findByUserStudentandnotdelete(@Param("classid") String classid);

}