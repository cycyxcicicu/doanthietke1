package com.example.cauhoi2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("stt ASC")
    List<RunPart> contents = new ArrayList<>();

    boolean isAnswer;
    boolean isNotMix;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    Question question;

    int stt;
}