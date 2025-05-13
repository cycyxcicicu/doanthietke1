package com.example.cauhoi2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Integer stt;
    @Column(nullable = false)
    String name;
    Date timeCreate;
    @Builder.Default
    Boolean isDeleted = false;
    @OneToMany(mappedBy = "category")
    List<Question> questions;
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    Course course;
    @PrePersist
    void onCreate() {
        timeCreate = new Date();
    }
}
