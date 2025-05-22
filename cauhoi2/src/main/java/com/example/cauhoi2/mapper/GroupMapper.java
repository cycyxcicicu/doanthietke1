package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.response.GroupResponse;
import com.example.cauhoi2.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupResponse toGroupResponse(Group group);

    GroupResponse cloneGroup(GroupResponse group);
}
