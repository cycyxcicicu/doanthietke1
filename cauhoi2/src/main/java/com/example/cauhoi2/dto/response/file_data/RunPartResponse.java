package com.example.cauhoi2.dto.response.file_data;

import com.example.cauhoi2.dto.response.ImageResponse;
import com.example.cauhoi2.entity.Image;
import com.example.cauhoi2.entity.file_data.RunPartType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RunPartResponse {
    String id;
    RunPartType type;
    List<ImageResponse> images = new ArrayList<>();
    String text;
    boolean isToDam;
    boolean isGachChan;
    boolean isInNghieng;
    boolean isEndLine;
    String maMauChu;
}
