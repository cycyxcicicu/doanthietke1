package com.example.cauhoi2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "exam_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    @OrderBy("stt ASC")
    List<Description> descriptions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    @OrderBy("stt ASC")
    List<Question> questions = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id")
    Exam exam;

    boolean isNotMix;

    int stt;
}
