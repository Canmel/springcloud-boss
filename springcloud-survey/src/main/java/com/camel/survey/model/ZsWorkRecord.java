package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveySignResult;
import com.camel.survey.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ZsWorkRecord extends BasePaginationEntity {

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
     * 申请者身份证号
     */
    @TableField("cid_Num")
    private String cIdNum;
    /**
     * 审批结果
     */
    private ZsSurveySignResult result;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createdAt;
    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;
    @TableField(exist = false)
    private ZsYesOrNo isApply;

    /**
     * 问卷主键
     */
    @TableField(exist = false)
    private Integer surveyId;

    @TableField(exist = false)
    private SysUser creator;
    @TableField(exist = false)
    private Integer creatorId;

}
