package com.example.cauhoi2.dto.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Data1 {
    String link;
}
