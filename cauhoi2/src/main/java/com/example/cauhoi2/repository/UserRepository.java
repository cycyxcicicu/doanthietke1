package com.example.cauhoi2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cauhoi2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    
}
