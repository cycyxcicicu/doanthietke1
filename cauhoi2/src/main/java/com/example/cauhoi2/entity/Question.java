package com.example.cauhoi2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("stt ASC")
    List<RunPart> contents = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    @OrderBy("stt ASC")
    List<Answer> answers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group group;

    int stt;
}