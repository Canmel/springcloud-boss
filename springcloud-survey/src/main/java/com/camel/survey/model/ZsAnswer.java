package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsTaskStatus;
import com.camel.survey.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'
 *              '::::::::::::'
 *                .::::::::::
 *           '::::::::::::::..
 *                ..::::::::::::.
 *              ``::::::::::::::::
 *               ::::``:::::::::'        .:::.
 *              ::::'   ':::::'       .::::::::.
 *            .::::'      ::::     .:::::::'::::.
 *           .:::'       :::::  .:::::::::' ':::::.
 *          .::'        :::::.:::::::::'      ':::::.
 *         .::'         ::::::::::::::'         ``::::.
 *     ...:::           ::::::::::::'              ``::.
 *    ```` ':.          ':::::::::'                  ::::..
 *                       '.:::::'                    ':'````..
 * < 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-12-17
 **/
@Data
public class ZsAnswer extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 问卷ID
     */
    private Integer surveyId;
    /**
     * 坐席号
     */
    private String seat;
    /**
     * 录音位置
     */
    private String record;
    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    /**
     * 创建人
     */
    private String creator;

    @TableField(value = "agent_UUID")
    private String agentUUID;

    @TableField(exist = false)
    private List<ZsAnswerItem> items;

    /**
     * 是否有效
     */
    private ZsYesOrNo valid;

    /**
     * 开始时间，用于查询
     */
    @TableField(exist = false)
    private String startDate;

    /**
     * 结束时间，用于查询
     */
    @TableField(exist = false)
    private String endDate;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务状态
     */
    private ZsTaskStatus taskStatus;

    /**
     * CDR信息
     */
    @TableField(exist = false)
    private ZsCdrinfo cdrinfo;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 时长
     */
    private String callLastsTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 复核意见
     */
    private String reviewMsg;

    /**
     * 复核结果
     */
    private Integer reviewStatus;

    /**
     * 复核人
     */
    @TableField(exist = false)
    private SysUser reviewer;

    /**
     * 复核人ID
     */
    @TableField("reviewer")
    private Integer reviewerId;

    /**
     * 复核人名称
     */
    private String reviewerName;

    /**
     * 复核时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date reviewerAt;

    private Integer uid;

    @TableField(exist = false)
    public SysUser user;

    private Integer checked;

    private Integer reviewSpent;

    /**
     * 工号
     */
    private String workNum;

    /**
     * 标签
     */
    public String label;

    public ZsAnswer() {
    }

    public ZsAnswer(Integer id) {
        this.id = id;
    }

    public ZsAnswer(Integer surveyId, String creator) {
        this.surveyId = surveyId;
        this.creator = creator;
    }

    public ZsAnswer(Integer id, String reviewMsg, Integer reviewStatus, Integer reviewerId, String reviewerName, Integer reviewSpent) {
        this.id = id;
        this.reviewMsg = reviewMsg;
        this.reviewStatus = reviewStatus;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewerAt = new Date();
        this.reviewSpent = reviewSpent;
    }

    @Override
    public String toString() {
        return "ZsAnswer{" +
                ", id=" + id +
                ", surveyId=" + surveyId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                "}";
    }
}
