package com.camel.survey.vo;

import lombok.Data;

@Data
public class SignRecordTotal {
    /**
     * 当月忘打卡次数
     */
    private Integer monthForgetCount;

    /**
     * 当月迟到次数
     */
    private Integer monthLateCount;

    /**
     * 当月缺勤旷工次数
     */
    private Integer monthAbsenceCount;

    /**
     * 当月早退次数
     */
    private Integer monthLeaveEarlyCount;

    /**
     * 当年调休
     */
    private Integer yearTakeOffCount;

    /**
     * 当年婚假
     */
    private Integer yearMarryingLeaveCount;

    /**
     * 当年年假
     */
    private Integer yearAnnualeaveCount;

    /**
     * 当年产假
     */
    private Integer yearMaternityLeaveCount;
}
