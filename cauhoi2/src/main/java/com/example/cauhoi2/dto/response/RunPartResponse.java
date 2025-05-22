package com.example.cauhoi2.dto.response;

import com.example.cauhoi2.entity.RunPartType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    Integer width;
    Integer height;
}
