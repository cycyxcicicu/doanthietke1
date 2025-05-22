package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.request.CourseCreateRequest;
import com.example.cauhoi2.dto.response.CourseResponse;
import com.example.cauhoi2.dto.response.ImageResponse;
import com.example.cauhoi2.dto.response.ImageUploadResponse;
import com.example.cauhoi2.entity.Course;
import com.example.cauhoi2.entity.Image;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageUploadResponse imageUploadResponse);
    ImageResponse toImageResponse(Image image);
}
