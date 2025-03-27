package com.example.cauhoi2.repository;

import com.example.cauhoi2.entity.InvalidedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidedRepository extends JpaRepository<InvalidedToken, String> {

} 