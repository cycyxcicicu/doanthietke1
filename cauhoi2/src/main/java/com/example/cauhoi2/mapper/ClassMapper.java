package com.example.cauhoi2.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.cauhoi2.dto.request.ClassCreationRequest;
import com.example.cauhoi2.dto.request.ClassUpdateRequest;
import com.example.cauhoi2.dto.response.ClassResponse;
import com.example.cauhoi2.entity.Clasz;

@Mapper(componentModel = "spring")

public interface ClassMapper {
    
    Clasz toClass(ClassCreationRequest request);

    ClassResponse toClassResponse(Clasz Class);
    
    List<ClassResponse> toListClassResponse(List<Clasz> Class);

    void toUpdateClass(@MappingTarget Clasz clazz, ClassUpdateRequest request);
    
}