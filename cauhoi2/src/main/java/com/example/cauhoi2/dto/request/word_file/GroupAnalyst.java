package com.example.cauhoi2.dto.request.word_file;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupAnalyst {
    String name;
    List<PartAnalyst> parts = new ArrayList<>();
}
