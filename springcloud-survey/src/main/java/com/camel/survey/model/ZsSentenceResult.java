package com.camel.survey.model;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
@Data
public class ZsSentenceResult extends ZsSurveyBaseEntity {

    private static final long serialVersionUID = 1L;

    private String taskId;
    /**
     * 该句所属音轨ID
     */
    private Integer channelId;
    /**
     * 该句的起始时间偏移，单位为毫秒
     */
    private Integer beginTime;
    /**
     * 该句的结束时间偏移，单位为毫秒
     */
    private Integer endTime;
    /**
     * 该句的识别文本结果
     */
    private String text;
    /**
     * 情绪能量值1-10，值越高情绪越强烈
     */
    private Double emotionValue;
    /**
     * 本句与上一句之间的静音时长，单位为秒
     */
    private Integer silenceDuration;
    /**
     * 本句的平均语速，单位为每分钟字数
     */
    private Integer speechRate;

    public ZsSentenceResult() {
    }

    public ZsSentenceResult(String taskId, Integer channelId, Integer beginTime, Integer endTime, String text, Double emotionValue, Integer silenceDuration, Integer speechRate) {
        this.taskId = taskId;
        this.channelId = channelId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.text = text;
        this.emotionValue = emotionValue;
        this.silenceDuration = silenceDuration;
        this.speechRate = speechRate;
    }

    @Override
    public String toString() {
        return "ZsSentenceResult{" +
        ", taskId=" + taskId +
        ", channelId=" + channelId +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", text=" + text +
        ", emotionValue=" + emotionValue +
        ", silenceDuration=" + silenceDuration +
        ", speechRate=" + speechRate +
        "}";
    }
}
