package com.camel.survey.vo;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class ZsWorkTotalInfo {
    private Integer answerCount;

    private Integer callTime;

    public Double baseNum() {
        if(!ObjectUtils.isEmpty(callTime) && !callTime.equals(0)) {
            return answerCount.doubleValue() / (60 * callTime.doubleValue());
        }
        return null;
    }
}
