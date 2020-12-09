package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author baily
 * @since 2020-12-09
 */
@Data
public class Customer extends ZsSurveyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 客户名称
     */
    private String username;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 职位
     */
    private String position;
    /**
     * 部门
     */
    private String dept;
    /**
     * 电话号码
     */
    private String tel;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 区县
     */
    private String county;
    /**
     * 街道/社区
     */
    private String street;
    /**
     * 地址
     */
    private String addr;
    /**
     * 行业
     */
    private String industry;
    /**
     * 包保人姓名
     */
    private String liabler;
    /**
     * 包保人电话
     */
    private String liablerTel;
    /**
     * 是否公开
     */
    private Integer open;

    @Override
    public String toString() {
        return "Customer{" +
                ", id=" + id +
                ", username=" + username +
                ", company=" + company +
                ", position=" + position +
                ", dept=" + dept +
                ", tel=" + tel +
                ", city=" + city +
                ", county=" + county +
                ", street=" + street +
                ", addr=" + addr +
                ", industry=" + industry +
                ", liabler=" + liabler +
                ", liablerTel=" + liablerTel +
                ", open=" + open +
                "}";
    }
}
