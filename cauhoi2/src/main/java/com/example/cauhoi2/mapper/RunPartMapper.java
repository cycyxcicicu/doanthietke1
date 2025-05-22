package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.RunPartResponse;
import com.example.cauhoi2.entity.RunPart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RunPartMapper {
    RunPartResponse toRunPartResponse(RunPart runPart);
}
