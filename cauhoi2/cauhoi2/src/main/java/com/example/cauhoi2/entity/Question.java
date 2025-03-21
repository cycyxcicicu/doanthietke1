package com.example.cauhoi2.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String questionsid;

    @ManyToOne
    @JoinColumn(name = "testid")
    @JsonIgnore
    Test test;
    
    @OneToMany(mappedBy = "question")
    List<History> history;

    String content;
    String answer;
    String choive;
    Boolean isdelete;
}
