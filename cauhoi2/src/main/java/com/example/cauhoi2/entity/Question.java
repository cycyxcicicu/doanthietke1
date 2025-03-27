package com.example.cauhoi2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String questionsId;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonIgnore
    Test test;
    
    @OneToMany(mappedBy = "question")
    List<History> history;

    String content;
    String answer;
    String choive;
    Boolean isDelete;
}
