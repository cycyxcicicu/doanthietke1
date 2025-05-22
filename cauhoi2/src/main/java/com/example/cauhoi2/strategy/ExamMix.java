package com.example.cauhoi2.strategy;

import com.example.cauhoi2.dto.response.ExamResponse;
import com.example.cauhoi2.dto.response.GroupResponse;
import com.example.cauhoi2.mapper.ExamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExamMix implements ExamMixStrategy {
    final ExamMapper examMapper;
    private final Map<String, GroupMixStrategy> mixStrategies;
    @Override
    public ExamResponse mix(ExamResponse exam) {
        ExamResponse clone = examMapper.cloneExam(exam);
        List<Integer> indexsMix = new ArrayList<>();
        List<Integer> indexsNoMix = new ArrayList<>();
        List<GroupResponse> groups = exam.getGroups();
        List<GroupResponse> newGroups = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            GroupResponse group = groups.get(i);
            if (groups.get(i).isNotMix() || group.getQuestions().isEmpty())
                indexsNoMix.add(i);
            else
                indexsMix.add(i);
            groups.set(i, mixGroup(group));
        }
        Collections.shuffle(indexsMix);
        for (int i = 0; i < groups.size(); i++) {
            if (indexsNoMix.contains(i)) {
                newGroups.add(groups.get(i));
                indexsNoMix.remove((Integer) i);
            } else {
                newGroups.add(groups.get(indexsMix.getFirst()));
                indexsMix.removeFirst();
            }
        }
        clone.setGroups(newGroups);
        return clone;
    }

    private GroupResponse mixGroup(GroupResponse group) {
        GroupMixStrategy strategy = mixStrategies.getOrDefault(group.getName(), mixStrategies.get("g3"));
        return strategy != null ? strategy.mix(group) : group;
    }
}
