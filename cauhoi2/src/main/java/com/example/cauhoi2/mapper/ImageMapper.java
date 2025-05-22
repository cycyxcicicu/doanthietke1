package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.ImageResponse;
import com.example.cauhoi2.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponse toImageResponse(Image image);
}
