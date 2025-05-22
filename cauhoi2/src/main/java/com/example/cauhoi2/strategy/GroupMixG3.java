package com.example.cauhoi2.strategy;

import com.example.cauhoi2.dto.response.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("g3")
@RequiredArgsConstructor
public class GroupMixG3 implements GroupMixStrategy {
    private final GroupMixG1 mixG1;
    private final GroupMixG2 mixG2;
    @Override
    public GroupResponse mix(GroupResponse groupResponse) {
        return mixG1.mix(mixG2.mix(groupResponse));
    }
}