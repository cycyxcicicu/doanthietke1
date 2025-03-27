package com.example.cauhoi2.entity;

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
    String testId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User userId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    Clasz classId;

    String name;
    String imageTest;
    Boolean isDelete;
    int thoiGian;
    @Builder.Default
    Boolean trangThai= true;
    @OneToMany(mappedBy = "test")  // Đảm bảo `Question` có trường `test`
    List<Question> questions;
}
