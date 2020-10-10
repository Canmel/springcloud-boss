package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
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
    @ExcelAnnotation(columnName = "phone", columnIndex = 0)
    private String mobile;

    /**
     * 姓名，用于导入
     */
    @ExcelAnnotation(columnName = "name", columnIndex = 1)
    @TableField(exist = false)
    private String name;

    /**
     * 城市，用于导入
     */
    @ExcelAnnotation(columnName = "city", columnIndex = 2)
    @TableField(exist = false)
    private String city;

    /**
     * 地址，用于导入
     */
    @ExcelAnnotation(columnName = "address", columnIndex = 3)
    @TableField(exist = false)
    private String address;

    /**
     *日期，用于导入
     */
    @ExcelAnnotation(columnName = "date", columnIndex = 4)
    @TableField(exist = false)
    private String date;

    /**
     * 类型，用于导入
     */
    @ExcelAnnotation(columnName = "type", columnIndex = 5)
    @TableField(exist = false)
    private String type;

    /**
     * 事项，用于导入
     */
    @ExcelAnnotation(columnName = "item", columnIndex = 6)
    @TableField(exist = false)
    private String item;

    /**
     * 问卷ID
     */
    private Integer surveyId;

//    @ExcelAnnotation(columnName = "电话号码信息", columnIndex = 1)
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
