package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;

import java.io.Serializable;

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
public class ZsAnswerItem extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer answerId;
    private Integer surveyId;
    private Integer questionId;
    private String question;
    private String value;
    private Integer type;
    @TableLogic
    private ZsStatus status;
    private Date createdAt;
    private String creator;
    private String option;
    private Integer optionId;
    private Integer uid;

    @TableField(exist = false)
    public SysUser user;

    /**
     * 是否有效
     */
    private ZsYesOrNo valid;

    /**
     * 选项
     */
    @TableField(exist = false)
    public ZsOption zsOption;

    @TableField(exist = false)
    public String seat;

    /**
     * 单题得分
     */
    public Integer score;

    public ZsAnswerItem() {
    }

    public ZsAnswerItem(String question, String option, Integer answerId, String value, Integer type, String creator) {
        this.question = question;
        this.option = option;
        this.answerId = answerId;
        this.value = value;
        this.type = type;
        this.creator = creator;
    }

    public ZsAnswerItem(Integer answerId, Integer surveyId, Integer questionId, String question, String value, Integer type, String creator, String option, Integer optionId, Integer uid) {
        this.answerId = answerId;
        this.surveyId = surveyId;
        this.questionId = questionId;
        this.question = question;
        this.value = value;
        this.type = type;
        this.creator = creator;
        this.option = option;
        this.optionId = optionId;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ZsAnswerItem{" +
                ", id=" + id +
                ", question=" + question +
                ", value=" + value +
                ", type=" + type +
                "}";
    }
}
