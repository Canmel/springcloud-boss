package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.*;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author baily
 * @since 2021-02-01
 */
@Data
public class ZsCallPlan extends ZsSurveyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;
    /**
     * 转接队列名称或IVR名称
     */
    private String transName;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 转接类型
     */
    private TransType transType;
    /**
     * 外线接入号
     */
    private String pstnNumber;
    /**
     * 任务状态
     */
    private TaskStatus taskStatus;
    /**
     * 最大并发数
     */
    private Integer maxConcurrentNum;
    /**
     * 语音文件链接，转IVR流程结合HTTP交互将播放该文件
     */
    private String playfileUrl;
    /**
     * AI话术名称
     */
    private String roName;

    /**
     * 外呼数
     */
    private String totCust;

    /**
     * 已呼数
     */
    private String totCallsuc;

    /**
     * 未呼数
     */
    private String totUncall;

    /**
     * 未接数
     */
    private String totFailure;

    /**
     * 已接数
     */
    private String totSuccess;

    /**
     * 转接数
     */
    private String totTocustomer;

    /**
     * 已呼率
     */
    private String totCallsucRate;

    /**
     * 未呼率
     */
    private String totUncallRate;

    /**
     * 已接通率
     */
    private String totSuccessRate;

    /**
     * 转接率
     */
    private String totTocustomerRate;

    /**
     * 命中率
     */
    private TaskHitRate hitRate;

    /**
     * 后处理时长
     */
    private Integer acwTime;

    /**
     * 重拨次数
     */
    private Integer redialTimes;

    /**
     * 重播间隔时长
     */
    private TaskRedialInterval minRedialInterval;

    /**
     * 最大振铃时长
     */
    private TaskRingTime maxRingTime;

    /**
     * 第三方校验URL
     */
    private Integer xintf;

    @Override
    public String toString() {
        return "ZsCallPlan{" +
        ", id=" + id +
        ", transName=" + transName +
        ", taskId=" + taskId +
        ", transType=" + transType +
        ", pstnNumber=" + pstnNumber +
        ", taskStatus=" + taskStatus +
        ", maxConcurrentNum=" + maxConcurrentNum +
        ", playfileUrl=" + playfileUrl +
        ", roName=" + roName +
        "}";
    }
}
