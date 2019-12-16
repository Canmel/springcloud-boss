package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveyCollectType;
import com.camel.survey.enums.ZsSurveyState;
import com.camel.survey.enums.ZsYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
 * < 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-12-06
 **/
@Data
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ZsSurvey extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;

    /**
     * 所属项目
     */
    @TableField(exist = false)
    private ZsProject project;

    /**
     * 所属项目ID
     */
    private Integer projectId;
    /**
     * 目标收集份数
     */
    private Integer collectNum;
    /**
     * 已收集份数
     */
    private Integer currentNum;

    /**
     * 收集方式
     */
    private ZsSurveyCollectType collectType;

    private ZsSurveyState state;

    @TableField(exist = false)
    private ZsYesOrNo isApplySuccess;

    /**
     * 所需要通过的考核
     */
    @TableField(exist = false)
    public List<Integer> exams;

    @TableField(exist = false)
    public List<ZsExam> examList;

    public ZsSurvey(Integer id) {
        this.id = id;
    }

    public ZsSurvey() {
    }

    public ZsSurvey(Integer projectId, ZsStatus status) {
        this.projectId = projectId;
        setStatus(status);
    }

    @Override
    public String toString() {
        return "ZsSurvey{" +
                ", id=" + id +
                ", name=" + name +
                ", projectId=" + projectId +
                ", collectNum=" + collectNum +
                ", currentNum=" + currentNum +
                "}";
    }
}
