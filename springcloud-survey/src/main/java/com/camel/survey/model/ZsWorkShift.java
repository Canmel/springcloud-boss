package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
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
    private Integer Id;
    /**
     * 名称
     */
    private String cname;
    /**
     * 举办地点
     */
    private String adress;
    /**
     * 该班级用户操作的状态
     */
    @TableField(exist = false)
    private ZsYesOrNo statusUser;
    @TableField(exist = false)
    private String statusUserId;

    /**
     * 试卷id
     */
    private Integer surveyId;
    /**
     * 起止时间
     */
    private String startTime;

    private String endTime;

    private String startDate;
    @TableField(exist = false)
    private String wtime;
    /**
     * 使用者idNum
     */
    @TableField(exist = false)
    private String idNUM;
    /**
     * 拼接试卷详情
     */
    @TableField(exist = false)
    private ZsSurvey survey;

    @TableField(exist = false)
    private ZsYesOrNo isApplySuccess;

}
