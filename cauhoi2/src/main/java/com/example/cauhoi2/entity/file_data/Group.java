package com.example.cauhoi2.entity.file_data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("stt ASC")
    List<DescriptionQuestion> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("stt ASC")
    List<Question> questions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "exam_id")
    Exam exam;

    int stt;
}
