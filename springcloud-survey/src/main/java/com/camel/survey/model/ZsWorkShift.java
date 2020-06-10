package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-04-14
 */
@Data
public class ZsWorkShift extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班次主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String cname;
    /**
     * 用户名
     */
    private String cuname;
    /**
     * 举办地点
     */
    private String address;
    /**
     * 该班次用户操作的状态
     */
    @TableField(exist = false)
    private ZsYesOrNo statusUser;

    /**
     * 问卷id
     */
    private Integer surveyId;
    /**
     * 起止时间
     */
    private String startTime;

    private String endTime;

    private String startDate;
    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;
    @TableField(exist = false)
    private String wtime;
    /**
     * 使用者idNum
     */
    @TableField(exist = false)
    private String idNum;
    /**
     * 拼接问卷详情
     */
    @TableField(exist = false)
    private ZsSurvey survey;

    @TableField(exist = false)
    private ZsYesOrNo isApplySuccess;
    /**
     * 输出通过门禁信息
     */
    @TableField(exist = false)
    private String zsAccessMsg;
}
