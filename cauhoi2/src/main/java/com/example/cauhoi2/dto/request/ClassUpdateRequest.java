package com.example.cauhoi2.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)

public class ClassUpdateRequest {
    String name;
    String imageClass;
    Boolean isDelete;
}
