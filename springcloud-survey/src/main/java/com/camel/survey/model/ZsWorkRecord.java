package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsSurveySignResult;
import com.camel.survey.enums.ZsYesOrNo;
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
public class ZsWorkRecord extends ZsSurveyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 班次主键
     */
    private Integer wsId;
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

    /**
     * 问卷主键
     */
    @TableField(exist = false)
    private Integer surveyId;

    @Override
    public String toString() {
        return "ZsWorkRecord{" +
        ", id=" + id +
        ", wsId=" + wsId +
        ", username=" + username +
        ", result=" + result +
        "}";
    }
}
