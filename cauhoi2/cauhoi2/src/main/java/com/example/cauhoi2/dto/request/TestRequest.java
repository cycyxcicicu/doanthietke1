package com.example.cauhoi2.dto.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.example.cauhoi2.entity.Question;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestRequest {
    String name;
    String imagetest;
    int thoigian;
    Boolean trangthai;
    List<Question> questions;
}
