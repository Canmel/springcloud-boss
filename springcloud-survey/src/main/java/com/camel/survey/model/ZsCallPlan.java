package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.TransType;
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
    /**
     * 转接队列名称或IVR名称
     */
    private String transName;
    /**
     * 任务ID
     */
    private Integer taskId;
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
    private String taskStatus;
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
