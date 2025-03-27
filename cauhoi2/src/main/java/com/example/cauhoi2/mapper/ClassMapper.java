package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.request.ClassCreationRequest;
import com.example.cauhoi2.dto.request.ClassUpdateRequest;
import com.example.cauhoi2.dto.response.ClassResponse;
import com.example.cauhoi2.entity.Clasz;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ClassMapper {
    
    Clasz toClass(ClassCreationRequest request);

    ClassResponse toClassResponse(Clasz Class);
    
    List<ClassResponse> toListClassResponse(List<Clasz> Class);

    void toUpdateClass(@MappingTarget Clasz clazz, ClassUpdateRequest request);
    
}