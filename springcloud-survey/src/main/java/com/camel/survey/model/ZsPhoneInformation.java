package com.camel.survey.model;

import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.survey.annotation.ExcelAnnotation;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-06-10
 */
@Data
public class ZsPhoneInformation extends ZsSurveyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 手机号码
     */
    @ExcelAnnotation(columnName = "手机号", columnIndex = 0)
    private String mobile;

  /**
     * 问卷ID
     */
    private Integer surveyId;

    @ExcelAnnotation(columnName = "电话号码信息", columnIndex = 1)
    private String information;


    @Override
    public String toString() {
        return "ZsPhoneInformation{" +
        ", id=" + id +
        ", mobile=" + mobile +
        ", surveyId=" + surveyId +
        ", information=" + information +
        "}";
    }
}
