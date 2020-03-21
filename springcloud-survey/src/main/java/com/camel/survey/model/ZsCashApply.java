package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;

/**
 * <p>
 * 提现省钱
 * </p>
 *
 * @author baily
 * @since 2020-03-21
 */
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
    private String works;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getExamBy() {
        return examBy;
    }

    public void setExamBy(Integer examBy) {
        this.examBy = examBy;
    }

    public Date getExamAt() {
        return examAt;
    }

    public void setExamAt(Date examAt) {
        this.examAt = examAt;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

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
}
