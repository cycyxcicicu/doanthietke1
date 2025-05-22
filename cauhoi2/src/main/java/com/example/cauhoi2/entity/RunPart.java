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
public class RunPart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    RunPartType type;

    @OneToMany
    @OrderBy("stt ASC")
    List<Image> images = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    String text;
    boolean isToDam;
    boolean isGachChan;
    boolean isInNghieng;
    boolean isEndLine;
    String maMauChu;
    int stt;

    Integer width;
    Integer height;
}
