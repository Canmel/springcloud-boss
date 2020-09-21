package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsApply;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 提现省钱
 * </p>
 *
 * @author baily
 * @since 2020-03-21
 */
@Data
public class ZsCashApply extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * openid
     */
    private String openid;
    /**
     * 用户名
     */
    private String uname;
    /**
     * 申请提现金额
     */
    private Double amount;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 审核人
     */
    private Integer examBy;
    /**
     * 审核时间
     */
    private Date examAt;
    /**
     * 涉及到的工作记录ID，备查
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String works;

    private ZsApply status;

    @TableField(exist = false)
    private String[] zsWorkId;

    /**
     * 开始时间，用于查询
     */
    @TableField(exist = false)
    private String startDate;

    /**
     * 结束时间，用于查询
     */
    @TableField(exist = false)
    private String endDate;

    @Override
    public String toString() {
        return "ZsCashApply{" +
        ", id=" + id +
        ", uid=" + uid +
        ", uname=" + uname +
        ", amount=" + amount +
        ", createdAt=" + createdAt +
        ", examBy=" + examBy +
        ", examAt=" + examAt +
        ", works=" + works +
        "}";
    }

    public String buildDesc() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return this.uname + "在" + sf.format(this.createdAt) + "发起的提现申请";
    }
}
