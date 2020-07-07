package com.camel.survey.vo;

import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.model.ZsWork;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Data
public class ProjectReport {
    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 基准量
     */
    private Double benchmark;

    /**
     * 平均数
     */
    private Double avgNum;

    /**
     * 基本工资
     */
    private Double baseSalary;

    /**
     * 提成
     */
    private Double royalty;

    /**
     * 餐补
     */
    private Double meals;

    /**
     * 工资
     */
    private Double salary;

    /**
     * 通话时间
     */
    private Double callTime;

    /**
     * 成功数量
     */
    private Double successNum;

    /**
     * 无效量
     */
    private Double invalidNum;

    /**
     * 有效量
     */
    private Double validNum;

    /**
     * 总的工作时长
     */
    private Double workHours;

    /**
     * 计算基准量
     *
     * @return
     */
    public Double getBenchmark() {
        if (!ObjectUtils.isEmpty(callTime) && !callTime.equals(new Double(0))) {
            return new BigDecimal(validNum / callTime * 60).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return 0.0;
    }

    /**
     * 计算平局值（平均每小时的总成功量）
     *
     * @return
     */
    public Double getAvgNum() {
        if (!ObjectUtils.isEmpty(this.workHours) && !this.workHours.equals(new Double(0))) {
            return new BigDecimal(successNum / workHours).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else{
            throw new SourceDataNotValidException("未统计到工作市场，需要更多的上报数据");
        }
    }

    /**
     * 计算工资
     * @return
     */
    public Double getBaseSalary(ZsWork work) {
        if(work.getAvgNum() < this.getBenchmark()) {
            return 18 * work.getWorkHours();
        } else {
            return 22 * work.getWorkHours();
        }
    }
}
