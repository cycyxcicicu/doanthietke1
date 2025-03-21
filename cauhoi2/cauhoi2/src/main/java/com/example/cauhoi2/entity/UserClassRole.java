package com.example.cauhoi2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class UserClassRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     String id;
    @ManyToOne
    @JoinColumn(name = "userid")
     User user;
    @ManyToOne
    @JoinColumn(name = "classid")
     Clasz clasz;
    @Enumerated(EnumType.STRING)
     Role role;  // Vai trò của người dùng trong lớp học
    public  enum Role {
        TEACHER,
        STUDENT
    }
    Boolean isdelete;
}
