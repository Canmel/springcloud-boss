package com.camel.survey.vo;

import com.camel.survey.model.ZsQuestion;
import lombok.Data;

import java.util.List;

@Data
public class ZsQuestionSave {

    public List<ZsQuestion> zsQuestions;
}
