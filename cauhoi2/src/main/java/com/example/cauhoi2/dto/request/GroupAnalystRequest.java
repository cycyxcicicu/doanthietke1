package com.example.cauhoi2.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupAnalystRequest {
    String name;
    List<PartAnalystRequest> parts = new ArrayList<>();
}
