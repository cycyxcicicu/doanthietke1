package com.example.cauhoi2.dto.response;

import java.util.List;

import com.example.cauhoi2.entity.Question;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class TestResponse {
    String testid;
    String name;
    String imagetest;
    int thoigian;
    List<Question> questions;
    Boolean trangthai;
}
