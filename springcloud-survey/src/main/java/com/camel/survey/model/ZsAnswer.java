package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsStatus;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
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

    public ZsAnswer() {
    }

    public ZsAnswer(Integer id) {
        this.id = id;
    }

    public ZsAnswer(Integer surveyId, String creator) {
        this.surveyId = surveyId;
        this.creator = creator;
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
