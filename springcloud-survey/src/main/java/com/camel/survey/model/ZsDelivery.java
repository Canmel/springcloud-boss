package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsAches;
import com.camel.survey.enums.ZsYesOrNo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'
 *              '::::::::::::'
 *                .::::::::::
 *           '::::::::::::::..
 *                ..::::::::::::.
 *              ``::::::::::::::::
 *               ::::``:::::::::'        .:::.
 *              ::::'   ':::::'       .::::::::.
 *            .::::'      ::::     .:::::::'::::.
 *           .:::'       :::::  .:::::::::' ':::::.
 *          .::'        :::::.:::::::::'      ':::::.
 *         .::'         ::::::::::::::'         ``::::.
 *     ...:::           ::::::::::::'              ``::.
 *    ```` ':.          ':::::::::'                  ::::..
 *                       '.:::::'                    ':'````..
 * <考核投递记录 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-12-12
 **/
@Data
public class ZsDelivery extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 考试ID
     */
    @TableField(exist = false)
    private ZsExam exam;

    /**
     * 成绩
     */
    private ZsAches ach;

    @TableField(exist = false)
    private ZsYesOrNo isDelivery;

    public ZsDelivery() {
    }

    public ZsDelivery(Integer examId) {
        this.examId = examId;
        this.setExam(new ZsExam(examId));
    }

    @Override
    public String toString() {
        return "ZsDelivery{" +
                ", id=" + id +
                ", examId=" + examId +
                ", ach=" + ach +
                "}";
    }
}
