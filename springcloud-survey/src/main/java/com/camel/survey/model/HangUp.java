package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-10-20
 */
@Data
public class HangUp extends ZsSurveyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String phone;
    private String reason;
    private Integer surveyId;

    @Override
    public String toString() {
        return "HangUp{" +
        ", id=" + id +
        ", phone=" + phone +
        ", reason=" + reason +
        ", surveyId=" + surveyId +
        "}";
    }
}
