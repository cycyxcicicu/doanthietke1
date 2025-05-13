package com.example.cauhoi2.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String content;
    @ElementCollection
    List<String> answers;
    @ElementCollection
    List<Integer> choices;
    @OneToMany
    List<Image> images;
    Integer countAnswer;
    @Enumerated(EnumType.STRING)
    LevelQuestion level;
    @Builder.Default
    Boolean isDeleted = false;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    Category category;
    Date timeCreate;
    @PrePersist
    void onCreate() {
        timeCreate = new Date();
    }
}