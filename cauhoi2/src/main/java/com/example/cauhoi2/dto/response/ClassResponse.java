package com.example.cauhoi2.dto.response;

import java.util.List;


import com.example.cauhoi2.entity.Test;
import com.example.cauhoi2.entity.User;
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
public class ClassResponse {
    String classid;
    String name;
    String imageclass;
    List<Test> testid;
    String role;
    boolean isdelete;
    String soluongthanhvien;
}
