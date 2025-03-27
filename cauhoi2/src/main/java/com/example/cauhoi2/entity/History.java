package com.example.cauhoi2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String historyId;
    
    @ManyToOne
    @JoinColumn(name = "questions_id")
    @JsonIgnore
    Question question; 
    
    String answers;
    String score;
    String testId;
    String userId;
    String number;
    
}
