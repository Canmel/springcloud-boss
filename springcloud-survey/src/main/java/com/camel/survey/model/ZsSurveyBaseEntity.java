package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.enums.ZsStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ZsSurveyBaseEntity extends BasePaginationEntity {

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    public ZsSurveyBaseEntity(Integer pageNum, Integer pageSize, Integer creatorId, SysUser creator, ZsStatus status, Date createdAt) {
        super(pageNum, pageSize);
        this.creatorId = creatorId;
        this.creator = creator;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ZsSurveyBaseEntity(Integer pageNum, Integer pageSize) {
        super(pageNum, pageSize);
    }

    public ZsSurveyBaseEntity() {
    }
}
