package com.example.cauhoi2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReadFileResponse {
    int soThuTu;
    String noiDung;
    String dapAnDung;
    String hinhAnhBase64;
    String nhomTron;
}
