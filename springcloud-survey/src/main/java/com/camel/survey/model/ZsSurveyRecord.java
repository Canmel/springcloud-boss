package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

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
 * <问卷下发或电话访问等发出记录 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-12-18
 **/
@Data
public class ZsSurveyRecord extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对象
     */
    private String target;
    /**
     * 问卷ID
     */
    private Integer surveyId;

    @TableField(exist = false)
    private ZsSurvey zsSurvey;
    /**
     * 冗余字段——问卷名称
     */
    private String surveyName;

    public ZsSurveyRecord() {
    }

    public ZsSurveyRecord(String target, Integer surveyId, String surveyName, Integer creatorId) {
        this.target = target;
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        setCreatorId(creatorId);
    }

    @Override
    public String toString() {
        return "ZsSurveyRecord{" +
                ", id=" + id +
                ", target=" + target +
                ", surveyId=" + surveyId +
                ", surveyName=" + surveyName +
                "}";
    }
}
