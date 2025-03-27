package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.Clasz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Clasz,String> {

    @Query("SELECT c FROM `class` c WHERE c.classId IN :classIds")
    List<Clasz> findClassesByClassIds(@Param("classIds") List<String> classIds);

    @Query("SELECT c FROM `class` c JOIN c.userClassRoles ucr WHERE ucr.user.username = :username")
    List<Clasz> findClassesByUserUsername(@Param("username") String username);
}
