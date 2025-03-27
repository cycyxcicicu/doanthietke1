package com.example.cauhoi2.dto.request;

import com.example.cauhoi2.entity.Question;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestRequest {
    String name;
    String imageTest;
    int thoiGian;
    Boolean trangThai;
    List<Question> questions;
}
