package com.example.cauhoi2.strategy;

import com.example.cauhoi2.dto.response.AnswerResponse;
import com.example.cauhoi2.dto.response.GroupResponse;
import com.example.cauhoi2.dto.response.QuestionResponse;
import com.example.cauhoi2.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component("g2")
@RequiredArgsConstructor
public class GroupMixG2 implements GroupMixStrategy {
    private final GroupMapper groupMapper;
    private final Random random;
    // Tron cau tra loi
    @Override
    public GroupResponse mix(GroupResponse group) {
        GroupResponse clone = groupMapper.cloneGroup(group);
        for (QuestionResponse questionResponse : clone.getQuestions()) {
            List<Integer> indexsMix = new ArrayList<>();
            List<Integer> indexsNoMix = new ArrayList<>();
            List<AnswerResponse> answers = questionResponse.getAnswers();
            List<AnswerResponse> newAnswers = new ArrayList<>();
            for (int i = 0; i < answers.size(); i++) {
                if (answers.get(i).isNotMix())
                    indexsNoMix.add(i);
                else
                    indexsMix.add(i);
            }
            Collections.shuffle(indexsMix);
            for (int i = 0; i < answers.size(); i++) {
                if (indexsNoMix.contains(i)) {
                    newAnswers.add(answers.get(i));
                    indexsNoMix.remove((Integer) i);
                }
                else {
                    newAnswers.add(answers.get(indexsMix.getFirst()));
                    indexsMix.removeFirst();
                }
            }
            questionResponse.setAnswers(newAnswers);
        }
        return clone;
    }
}