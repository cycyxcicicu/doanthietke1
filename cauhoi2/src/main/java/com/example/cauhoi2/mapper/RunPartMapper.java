package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.file_data.RunPartResponse;
import com.example.cauhoi2.entity.file_data.RunPart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RunPartMapper {
    RunPartResponse toRunPartResponse(RunPart runPart);
}
