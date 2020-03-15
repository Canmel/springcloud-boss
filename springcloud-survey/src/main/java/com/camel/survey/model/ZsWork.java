package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.annotation.ExcelAnnotation;
import lombok.Data;

/**
 * <p>
 * 访员工作记录
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
@Data
public class ZsWork extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 项目主键
     */
    private Integer projectId;
    /**
     * 项目名称
     */
    @ExcelAnnotation(columnIndex = 0, columnName = "项目名称")
    private String pname;

    /**
     * 访员名
     */
    @ExcelAnnotation(columnIndex = 3, columnName = "访员名")
    private String uname;

    @ExcelAnnotation(columnIndex = 1, columnName = "项目时间")
    private String ptime;
    /**
     * 日期
     */
    @ExcelAnnotation(columnIndex = 2, columnName = "日期")
    private Date workDate;
    /**
     * 手机号
     */
    @ExcelAnnotation(columnIndex = 4, columnName = "手机号")
    private String phone;
    /**
     * 身份证
     */
    @ExcelAnnotation(columnIndex = 5, columnName = "身份证")
    private String idNum;
    /**
     * 工号
     */
    @ExcelAnnotation(columnIndex = 6, columnName = "工号")
    private String jobNumber;
    /**
     * 接触量
     */
    @ExcelAnnotation(columnIndex = 7, columnName = "接触量")
    private Integer tryNum;
    /**
     * 成功量
     */
    @ExcelAnnotation(columnIndex = 8, columnName = "成功量")
    private Integer succssNum;
    /**
     * 无效量
     */
    @ExcelAnnotation(columnIndex = 9, columnName = "作废量")
    private Integer invalidNum;
    /**
     * 有效量
     */
    @ExcelAnnotation(columnIndex = 10, columnName = "有效量")
    private Integer validNum;
    /**
     * 成功率
     */
    @ExcelAnnotation(columnIndex = 11, columnName = "成功率")
    private String successRate;
    /**
     * 开始时间
     */
    @ExcelAnnotation(columnIndex = 12, columnName = "开始时间")
    private Date startTime;
    /**
     * 结束时间
     */
    @ExcelAnnotation(columnIndex = 13, columnName = "结束时间")
    private Date endTime;
    /**
     * 就餐时间
     */
    @ExcelAnnotation(columnIndex = 14, columnName = "就餐时间")
    private Double eatTime;
    /**
     * 工作时长
     */
    @ExcelAnnotation(columnIndex = 15, columnName = "工作时长")
    private Double workHours;
    /**
     * 基准量
     */
    @ExcelAnnotation(columnIndex = 16, columnName = "基准量")
    private Double benchmark;
    /**
     * 底薪
     */
    @ExcelAnnotation(columnIndex = 18, columnName = "底薪")
    private Double baseSalary;
    /**
     * 提成
     */
    @ExcelAnnotation(columnIndex = 19, columnName = "提成")
    private Double royalty;
    /**
     * 餐费
     */
    @ExcelAnnotation(columnIndex = 21, columnName = "餐费")
    private Double meals;
    /**
     * 薪水
     */
    @ExcelAnnotation(columnIndex = 23, columnName = "薪水")
    private Double salary;
    /**
     * 是否获取,从工作薪酬提出到余额
     */
    private Integer gain;
    /**
     * 获取时间，从工作薪酬提出到余额
     */
    private Date gainTime;

    @Override
    public String toString() {
        return "ZsWork{" +
        ", id=" + id +
        ", projectId=" + projectId +
        ", pname=" + pname +
        ", ptime=" + ptime +
        ", workDate=" + workDate +
        ", phone=" + phone +
        ", idNum=" + idNum +
        ", jobNumber=" + jobNumber +
        ", tryNum=" + tryNum +
        ", succssNum=" + succssNum +
        ", invalidNum=" + invalidNum +
        ", validNum=" + validNum +
        ", successRate=" + successRate +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", eatTime=" + eatTime +
        ", workHours=" + workHours +
        ", benchmark=" + benchmark +
        ", baseSalary=" + baseSalary +
        ", royalty=" + royalty +
        ", meals=" + meals +
        ", salary=" + salary +
        ", gain=" + gain +
        ", gainTime=" + gainTime +
        "}";
    }
}
