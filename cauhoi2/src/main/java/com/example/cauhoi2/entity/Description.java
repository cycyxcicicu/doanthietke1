package com.example.cauhoi2.entity.file_data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DescriptionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("stt ASC")
    List<RunPart> contents = new ArrayList<>();

    @ManyToOne

    int stt;
}