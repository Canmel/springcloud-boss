package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 项目基准
 * </p>
 *
 * @author baily
 * @since 2020-07-08
 */
public class ZsSurveyBenchmark extends BasePaginationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 项目名称
     */
    private Integer pid;
    /**
     * 工作日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    /**
     * 排行榜前十名样本平均数
     */
    private Double avgTopNum;
    /**
     * 工作时间
     */
    private Double workHours;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Double getAvgTopNum() {
        return avgTopNum;
    }

    public void setAvgTopNum(Double avgTopNum) {
        this.avgTopNum = avgTopNum;
    }

    public Double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

    @Override
    public String toString() {
        return "ZsSurveyBenchmark{" +
        ", id=" + id +
        ", pid=" + pid +
        ", workDate=" + workDate +
        ", avgTopNum=" + avgTopNum +
        ", workHours=" + workHours +
        "}";
    }
}
