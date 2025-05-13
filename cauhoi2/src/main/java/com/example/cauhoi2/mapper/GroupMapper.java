package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.file_data.GroupResponse;
import com.example.cauhoi2.entity.file_data.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupResponse toGroupResponse(Group group);
}
