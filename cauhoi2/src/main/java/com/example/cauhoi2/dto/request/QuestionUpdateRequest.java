package com.example.cauhoi2.dto.request;

import java.util.List;

import com.example.cauhoi2.entity.LevelQuestion;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class QuestionUpdateRequest {
    @NotBlank
    String id;
    String content;
    List<Integer> choices;
    LevelQuestion level;
    String categoryId;
    List<String> answers;
}
