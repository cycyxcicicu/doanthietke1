package com.example.cauhoi2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
