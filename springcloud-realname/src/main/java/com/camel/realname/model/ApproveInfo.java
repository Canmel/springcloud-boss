package com.camel.realname.model;

import com.camel.core.entity.BasePaginationEntity;
import com.camel.realname.enums.ApproveType;
import com.camel.realname.enums.ZsStatus;
import com.camel.realname.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveInfo extends BasePaginationEntity {
    /**
     * 序号
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型 1： 企业 ，2：外呼号码 ，3：个人；
     */
    private ApproveType type;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 状态 0：未通过，1：已通过
     */
    private ZsYesOrNo apply;

    /**
     * 是否有效，0：无效，1有效
     */
    private ZsStatus status;
}
