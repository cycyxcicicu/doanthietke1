package com.example.cauhoi2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.cauhoi2.dto.request.UserCreateRequest;
import com.example.cauhoi2.dto.request.UserUpdateRequest;
import com.example.cauhoi2.dto.response.UserResponse;
import com.example.cauhoi2.dto.response.UserofClassResponse;
import com.example.cauhoi2.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

@Mapper(componentModel = "spring")

public interface UserMapper {
    User toUser (UserCreateRequest request);
    void toupdate(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toUserResponse(User user);
    UserofClassResponse toUserofClassResponse(User user);
} 