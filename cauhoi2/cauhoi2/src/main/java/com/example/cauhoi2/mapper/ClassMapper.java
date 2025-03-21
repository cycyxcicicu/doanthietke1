package com.example.cauhoi2.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.cauhoi2.dto.request.ClassCreationRequest;
import com.example.cauhoi2.dto.request.ClassUpdateRequest;
import com.example.cauhoi2.dto.response.ClassResponse;
import com.example.cauhoi2.entity.Clasz;
import com.fasterxml.jackson.annotation.JsonInclude;

@Mapper(componentModel = "spring")

public interface ClassMapper {
    
    Clasz toClass(ClassCreationRequest request);

    ClassResponse toClassResponse(Clasz Class);
    
    List<ClassResponse> toListClassResponse(List<Clasz> Class);

    @Mapping(target = "classid", ignore = true)
    void toupdateClass(@MappingTarget Clasz clazz, ClassUpdateRequest request);
    
}