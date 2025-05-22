package com.example.cauhoi2.strategy;

import com.example.cauhoi2.dto.response.GroupResponse;
import org.springframework.stereotype.Component;

@Component("g0")
public class GroupMixG0 implements GroupMixStrategy {

    // Khong can tron
    @Override
    public GroupResponse mix(GroupResponse group) {
        return group;
    }
}