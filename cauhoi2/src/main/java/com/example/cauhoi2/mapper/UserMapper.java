package com.example.cauhoi2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.cauhoi2.dto.request.UserCreateRequest;
import com.example.cauhoi2.dto.request.UserUpdateRequest;
import com.example.cauhoi2.dto.response.UserResponse;
import com.example.cauhoi2.dto.response.UserOfClassResponse;
import com.example.cauhoi2.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser (UserCreateRequest request);
    void toUpdate(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toUserResponse(User user);
    UserOfClassResponse toUserofClassResponse(User user);
} 