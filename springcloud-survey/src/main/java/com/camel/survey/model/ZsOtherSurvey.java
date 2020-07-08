package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.enums.ZsOtherSurveyType;
import lombok.Data;

/**
 * <p>
 * 其他平台问卷
 * </p>
 *
 * @author baily
 * @since 2020-06-18
 */
@Data
public class ZsOtherSurvey  extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 问卷名称
     */
    private String name;

    private Integer state;

    private String place;

    private String ptime;

    private ZsOtherSurveyType type;

    @Override
    public String toString() {
        return "ZsOtherSurvey{" +
        ", id=" + id +
        ", name=" + name +
        ", place=" + place +
        ", ptime=" + ptime +
        ", type=" + type +
        "}";
    }
}
