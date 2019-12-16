package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveySignResult;
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
 * <问卷调查报名记录 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-12-13
 **/
@Data
public class ZsSign extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 问卷主键
     */
    private Integer surveyId;
    /**
     * 问卷对象
     */
    @TableField(exist = false)
    private ZsSurvey survey;
    /**
     * 用户名
     */
    private String username;
    /**
     * 审批结果
     */
    private ZsSurveySignResult result;

    @TableField(exist = false)
    private ZsYesOrNo isApply;

    public ZsSign(Integer surveyId, String username, Integer creator) {
        this.surveyId = surveyId;
        this.username = username;
        setCreatorId(creator);
    }

    public ZsSign() {
    }

    @Override
    public String toString() {
        return "ZsSign{" +
                ", id=" + id +
                ", username=" + username +
                ", result=" + result +
                "}";
    }
}
