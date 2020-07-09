package com.camel.survey.vo;

import cn.hutool.core.math.MathUtil;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.model.ZsOtherSurvey;
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
     * 基准量 = 平均数 * 系数 / 工作时间
     * @return
     */
    public Double loadBenchmark(ZsOtherSurvey survey) {
        if(!ObjectUtils.isEmpty(survey.getRatio()) && survey.getRatio() > 0) {
            if (!ObjectUtils.isEmpty(this.workHours) && !workHours.equals(new Double(0)) && !ObjectUtils.isEmpty(avgNum) && !ObjectUtils.isEmpty(workHours)) {
                return new BigDecimal(survey.getRatio() * avgNum / workHours).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }else {
                throw new SourceDataNotValidException("前十平均数未设置");
            }
        }
        throw new SourceDataNotValidException("考核系数未设置");
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
    public Double getBaseSalary(ZsWork work, ZsOtherSurvey survey) {
        if(work.getAvgNum() < this.loadBenchmark(survey)) {
            return 18 * work.getWorkHours();
        } else {
            return 22 * work.getWorkHours();
        }
    }
}
