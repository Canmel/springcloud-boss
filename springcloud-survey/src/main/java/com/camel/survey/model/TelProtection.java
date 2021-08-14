package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.survey.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 最终客户授权电话
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelProtection extends BasePaginationEntity implements Serializable{
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 最终客户 用户id
     */
    private Integer finalCusId;
    /**
     * 外呼号码
     */
    private String tel;
    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 项目
     */
    @TableField(exist = false)
    private ZsProject zsProject;
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;
    /**
     * 状态
     */
    @TableLogic
    private ZsYesOrNo status;

    /**
     * 供应商 用户id 和名字
     */
    @TableField(exist = false)
    private Integer partnerId;

//    @TableField(exist = false)
//    private List<SysUser> partner;

    @TableField(exist = false)
    private List<SysCompany> company;

    /**
     * 项目名
     */
    @TableField(exist = false)
    private String projectName;

    /**
     * 最终用户名
     */
    @TableField(exist = false)
    private String userName;


}
