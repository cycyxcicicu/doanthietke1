package com.example.cauhoi2.dto.request.word_file;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.ArrayList;
import java.util.List;
//

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartAnalystRequest {
    List<XWPFParagraph> values = new ArrayList<>();
    boolean isQuestion;
    boolean addedAnswered;
}
