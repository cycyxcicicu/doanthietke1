package com.example.cauhoi2.entity.file_data;

import com.example.cauhoi2.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("stt ASC")
    List<Group> groups = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}