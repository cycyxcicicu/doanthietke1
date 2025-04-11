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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Integer stt;
    @Column(nullable = false, unique = true)
    String name;
    Date timeCreate;
    @Builder.Default
    Boolean isDeleted = false;
    @OneToMany(mappedBy = "course")
    List<Category> categories;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    User user;
    @PrePersist
    void onCreate() {
        timeCreate = new Date();
    }
}
