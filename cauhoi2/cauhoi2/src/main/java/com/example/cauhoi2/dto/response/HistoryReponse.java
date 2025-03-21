package com.example.cauhoi2.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryReponse {
    String historyid;
    String questionid;
    String answers;
    String score;
    String userid;
    String testid;
    String number;
}
