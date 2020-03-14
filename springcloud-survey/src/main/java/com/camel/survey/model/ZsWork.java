package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;

/**
 * <p>
 * 访员工作记录
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
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
    private String pname;
    private String ptime;
    /**
     * 日期
     */
    private Date workDate;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份证
     */
    private String idNum;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 接触量
     */
    private Integer tryNum;
    /**
     * 成功量
     */
    private Integer succssNum;
    /**
     * 无效量
     */
    private Integer invalidNum;
    /**
     * 有效量
     */
    private Integer validNum;
    /**
     * 成功率
     */
    private String successRate;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 就餐时间
     */
    private Date eatTime;
    /**
     * 工作时长
     */
    private Double workHours;
    /**
     * 基准量
     */
    private Double benchmark;
    /**
     * 底薪
     */
    private Double baseSalary;
    /**
     * 提成
     */
    private Double royalty;
    /**
     * 餐费
     */
    private Double meals;
    /**
     * 薪水
     */
    private Double salary;
    /**
     * 是否获取,从工作薪酬提出到余额
     */
    private Integer gain;
    /**
     * 获取时间，从工作薪酬提出到余额
     */
    private Date gainTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getTryNum() {
        return tryNum;
    }

    public void setTryNum(Integer tryNum) {
        this.tryNum = tryNum;
    }

    public Integer getSuccssNum() {
        return succssNum;
    }

    public void setSuccssNum(Integer succssNum) {
        this.succssNum = succssNum;
    }

    public Integer getInvalidNum() {
        return invalidNum;
    }

    public void setInvalidNum(Integer invalidNum) {
        this.invalidNum = invalidNum;
    }

    public Integer getValidNum() {
        return validNum;
    }

    public void setValidNum(Integer validNum) {
        this.validNum = validNum;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEatTime() {
        return eatTime;
    }

    public void setEatTime(Date eatTime) {
        this.eatTime = eatTime;
    }

    public Double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

    public Double getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(Double benchmark) {
        this.benchmark = benchmark;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Double getRoyalty() {
        return royalty;
    }

    public void setRoyalty(Double royalty) {
        this.royalty = royalty;
    }

    public Double getMeals() {
        return meals;
    }

    public void setMeals(Double meals) {
        this.meals = meals;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getGain() {
        return gain;
    }

    public void setGain(Integer gain) {
        this.gain = gain;
    }

    public Date getGainTime() {
        return gainTime;
    }

    public void setGainTime(Date gainTime) {
        this.gainTime = gainTime;
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
