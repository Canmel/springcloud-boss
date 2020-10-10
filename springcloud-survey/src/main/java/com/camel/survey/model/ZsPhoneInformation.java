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
    @ExcelAnnotation(columnName = "手机号", columnIndex = 0)
    private String mobile;

    /**
     * 姓名，用于导入
     */
    @ExcelAnnotation(columnName = "姓名", columnIndex = 1)
    @TableField(exist = false)
    private String name;

    /**
     * 城市，用于导入
     */
    @ExcelAnnotation(columnName = "城市", columnIndex = 2)
    @TableField(exist = false)
    private String city;

    /**
     * 地址，用于导入
     */
    @ExcelAnnotation(columnName = "地址", columnIndex = 3)
    @TableField(exist = false)
    private String address;

    /**
     *日期，用于导入
     */
    @ExcelAnnotation(columnName = "日期", columnIndex = 4)
    @TableField(exist = false)
    private String date;

    /**
     * 类型，用于导入
     */
    @ExcelAnnotation(columnName = "类型", columnIndex = 5)
    @TableField(exist = false)
    private String type;

    /**
     * 事项，用于导入
     */
    @ExcelAnnotation(columnName = "事项", columnIndex = 6)
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
