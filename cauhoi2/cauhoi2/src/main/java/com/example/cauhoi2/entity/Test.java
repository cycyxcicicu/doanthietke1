package com.example.cauhoi2.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String testid;

    @ManyToOne
    @JoinColumn(name = "userid")
    User userid;

    @ManyToOne
    @JoinColumn(name = "classid")
    Clasz classid;

    String name;
    String imagetest;
    Boolean isdelete;
    int thoigian;
    Boolean trangthai= true;
    @OneToMany(mappedBy = "test")  // Đảm bảo `Question` có trường `test`
    List<Question> questions;
}
