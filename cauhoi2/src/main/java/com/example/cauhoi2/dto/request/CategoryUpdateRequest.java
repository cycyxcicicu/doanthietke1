package com.example.cauhoi2.dto.request;



import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryUpdateRequest {
    String id;
    @NotBlank(message = "Tên danh mục không được để trống")
    String name;
    @NotBlank
    String courseid;
}
