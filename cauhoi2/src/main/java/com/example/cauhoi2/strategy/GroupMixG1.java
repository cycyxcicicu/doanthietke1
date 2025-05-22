package com.example.cauhoi2.strategy;

import com.example.cauhoi2.dto.response.GroupResponse;
import com.example.cauhoi2.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("g1")
@RequiredArgsConstructor
public class GroupMixG1 implements GroupMixStrategy {
    private final GroupMapper groupMapper;
    // Tron cau hoi
    @Override
    public GroupResponse mix(GroupResponse group) {
        GroupResponse clone = groupMapper.cloneGroup(group);
        Collections.shuffle(clone.getQuestions());
        return clone;
    }
}