package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.response.DescriptionResponse;
import com.example.cauhoi2.dto.response.ExamResponse;
import com.example.cauhoi2.dto.response.GroupResponse;
import com.example.cauhoi2.dto.response.RunPartResponse;
import com.example.cauhoi2.entity.Exam;
import com.example.cauhoi2.entity.Group;
import com.example.cauhoi2.entity.RunPart;
import com.example.cauhoi2.strategy.ExamMix;
import com.example.cauhoi2.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MixService {
    final ExamMix examMix;
    public List<ExamResponse> mix(ExamResponse exam, int count) {
        List<ExamResponse> exams = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ExamResponse examRes = examMix.mix(exam);
            mapping(examRes);
            exams.add(examRes);
        }
        return exams;
    }

    private void mapping(ExamResponse exam) {
        int sttQuestion = 1;
        for (GroupResponse group : exam.getGroups()) {
            if (group.getQuestions().isEmpty()) continue;
            int countMapping = countMapping(group);
            if (countMapping == 0 || (countMapping != 2 && countMapping != group.getQuestions().size() && countMapping != 2 + group.getQuestions().size())) {
                sttQuestion += group.getQuestions().size();
                continue;
            }
            List<Integer> listReplace = getListReplace(group, countMapping, sttQuestion);
            replace(group, listReplace);
            sttQuestion += group.getQuestions().size();
        }
    }

    private static List<Integer> getListReplace(GroupResponse group, int countMapping, int sttQuestion) {
        List<Integer> listReplace = new LinkedList<>();
        if (countMapping == 2 || countMapping == 2 + group.getQuestions().size()) {
            listReplace.add(sttQuestion);
            listReplace.add(sttQuestion + group.getQuestions().size() - 1);
        }
        if (countMapping == group.getQuestions().size() || countMapping == 2 + group.getQuestions().size())
            for (int i = sttQuestion; i < sttQuestion + group.getQuestions().size(); i++) {
                listReplace.add(i);
            }
        return listReplace;
    }

    private int countMapping(GroupResponse group) {
        if (group.getQuestions().isEmpty())
            return -1;
        int count = 0;
        for (DescriptionResponse description : group.getDescriptions()) {
            count += description.getContents().stream().mapToInt(item -> Util.countStr2InStr1(item.getText(), "{}")).sum();
        }
        return count;
    }

    private void replace(GroupResponse group, List<Integer> listReplace) {
        for (DescriptionResponse des : group.getDescriptions()) {
            if (listReplace.isEmpty()) return;
            for (RunPartResponse runPart : des.getContents()) {
                if (listReplace.isEmpty()) return;
                while (runPart.getText() != null && runPart.getText().contains("{}") && !listReplace.isEmpty()) {
                    String text = runPart.getText();
                    text = text.replaceFirst(Pattern.quote("{}"), listReplace.getFirst().toString());
                    runPart.setText(text);
                    listReplace.removeFirst();
                }
            }
        }
    }
}
