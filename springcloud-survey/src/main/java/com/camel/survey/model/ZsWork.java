package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.annotation.ExcelAnnotation;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsWorkSource;
import com.camel.survey.enums.ZsWorkState;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.exceptions.SurveyNotValidException;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsWorkService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "项目不能为空")
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "日期不能为空")
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
    @NotBlank(message = "工号不能为空")
    @ExcelAnnotation(columnIndex = 6, columnName = "工号")
    private String jobNumber;
    /**
     * 接触量
     */
    @NotNull(message = "接触量不能为空")
    @ExcelAnnotation(columnIndex = 7, columnName = "接触量")
    private Integer tryNum;
    /**
     * 成功量
     */
    @NotNull(message = "成功量不能为空")
    @ExcelAnnotation(columnIndex = 8, columnName = "成功量")
    private Integer successNum;
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
    @NotNull(message = "开始时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelAnnotation(columnIndex = 12, columnName = "开始时间")
    private Date startTime;
    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelAnnotation(columnIndex = 13, columnName = "结束时间")
    private Date endTime;
    /**
     * 就餐时间
     */
    @NotNull(message = "休息时间不能为空")
    @ExcelAnnotation(columnIndex = 14, columnName = "就餐时间")
    private Double eatTime;
    /**
     * 工作时长
     */
    @ExcelAnnotation(columnIndex = 15, columnName = "工作时长")
    private Double workHours;

    @NotNull(message = "通话时长不能为空")
    private Double callTime;
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
     * 地点
     */
    @ExcelAnnotation(columnIndex = 29, columnName = "地点")
    private String place;


    private ZsWorkState state;

    private Double avgNum;

    /**
     * 状态
     */
    @TableLogic
    private ZsStatus status;
    /**
     * 是否获取,从工作薪酬提出到余额
     */
    private Integer gain;
    /**
     * 获取时间，从工作薪酬提出到余额
     */
    private Date gainTime;

    /**
     * 指出该工作记录数据来源
     */
    private ZsWorkSource source;

    private Integer uid;

    public ZsWork(Integer id, Integer gain) {
        this.id = id;
        this.gain = gain;
    }

    public ZsWork() {
    }

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
                ", succssNum=" + successNum +
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
                ", source=" + source +
                ", uid=" + uid +
                ", state=" + state +
                "}";
    }

    public void buildNecessaryAttribute(ZsSurveyService service, SysUser user) {
        this.validEntity();
        if (ObjectUtils.isEmpty(user.getIdNum())) {
            throw new SourceDataNotValidException("您还未完善个人身份证信息，请先完善");
        }
        if (!ObjectUtils.isEmpty(this.projectId)) {
            ZsSurvey survey = service.selectById(this.projectId);
            if (ObjectUtils.isEmpty(survey)) {
                throw new SourceDataNotValidException("未选择任何问卷");
            }
            setProjectId(survey.getId());
            setPname(survey.getName());
        }
        setUname(user.getUsername());
        setIdNum(user.getIdNum());
        setPhone(user.getMobile());
        setUid(user.getUid());
        resetSuccessRate();
    }

    public void validEntity() {
        if(successNum > tryNum) {
            throw new SourceDataNotValidException("成功量不能大于接触量");
        }
        if (ObjectUtils.isEmpty(this.getStartTime()) || ObjectUtils.isEmpty(this.getEndTime())) {
            throw new SourceDataNotValidException("开始时间与结束时间不能是一个空值");
        }
        if (this.startTime.getTime() >= this.endTime.getTime()) {
            throw new SourceDataNotValidException("结束时间不能是一个小于开始时间的值");
        }
        if(DateUtils.addHours(this.startTime, this.eatTime.intValue()).getTime() >= this.endTime.getTime()) {
            throw new SourceDataNotValidException("上班时间已经全部休息了，本次不需要上报");
        }
    }

    public Integer getValidNum() {
        if(ObjectUtils.isEmpty(validNum)) {
            return successNum;
        }
        return ObjectUtils.isEmpty(invalidNum) ? successNum : successNum - invalidNum;
    }

    public String getSuccessRate() {
        if (StringUtils.isNotEmpty(successRate)) {
            return successRate;
        }
        if (!ObjectUtils.isEmpty(tryNum) && tryNum > 0) {
            Double f1 = new BigDecimal((float) 100 * getValidNum() / tryNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return f1.toString();
        }
        return successRate;
    }

    public void resetSuccessRate() {
        if (!ObjectUtils.isEmpty(tryNum) && tryNum > 0 && !ObjectUtils.isEmpty(getValidNum())) {
            Double f1 = new BigDecimal((float) 100 * getValidNum() / tryNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            this.successRate = f1.toString();
        }
    }
}
