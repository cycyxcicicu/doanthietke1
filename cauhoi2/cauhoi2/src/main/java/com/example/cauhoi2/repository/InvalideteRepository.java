package com.example.cauhoi2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.InvalideteToken;

@Repository
public interface InvalideteRepository extends JpaRepository<InvalideteToken, String> {

} 