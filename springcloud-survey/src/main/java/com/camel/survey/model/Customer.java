package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.survey.annotation.ExcelAnnotation;
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
     * 电话号码
     */
    @ExcelAnnotation(columnName = "电话号码", columnIndex = 1)
    @TableId(value = "tel", type = IdType.ID_WORKER_STR)
    private String tel;
    /**
     * 客户名称
     */
    @ExcelAnnotation(columnName = "客户名称", columnIndex = 2)
    private String username;
    /**
     * 公司名称
     */
    @ExcelAnnotation(columnName = "公司名称", columnIndex = 3)
    private String company;
    /**
     * 职位
     */
    @ExcelAnnotation(columnName = "职位", columnIndex = 4)
    private String position;
    /**
     * 部门
     */
    @ExcelAnnotation(columnName = "部门", columnIndex = 5)
    private String dept;
    /**
     * 所在城市
     */
    @ExcelAnnotation(columnName = "所在城市", columnIndex = 6)
    private String city;
    /**
     * 区县
     */
    @ExcelAnnotation(columnName = "区县", columnIndex = 7)
    private String county;
    /**
     * 街道/社区
     */
    @ExcelAnnotation(columnName = "街道/社区", columnIndex = 8)
    private String street;
    /**
     * 地址
     */
    @ExcelAnnotation(columnName = "地址", columnIndex = 9)
    private String addr;
    /**
     * 行业
     */
    @ExcelAnnotation(columnName = "行业", columnIndex = 10)
    private String industry;
    /**
     * 包保人姓名
     */
    @ExcelAnnotation(columnName = "包保人姓名", columnIndex = 11)
    private String liabler;
    /**
     * 包保人电话
     */
    @ExcelAnnotation(columnName = "包保人电话", columnIndex = 12)
    private String liablerTel;
    /**
     * 是否公开
     */
    private Integer open;


    @Override
    public String toString() {
        return "Customer{" +
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
