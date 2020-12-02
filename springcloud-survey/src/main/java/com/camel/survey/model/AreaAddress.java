package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.survey.annotation.ExcelAnnotation;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@Data
public class AreaAddress extends BasePaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelAnnotation(columnIndex = 1, columnName = "乡镇/街道")
    @TableField(exist = false)
    private String area;

    @ExcelAnnotation(columnIndex = 2, columnName = "村/社区/小区")
    @TableField(exist = false)
    private String address;

    /**
     * 名称
     */
    private String name;
    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 所属区域（省市县）
     */
    private Integer areaId;

    /**
     * 首字母
     */
    private String initials;

    @Override
    public String toString() {
        return "AreaAddress{" +
        ", id=" + id +
        ", name=" + name +
        ", pinyin=" + pinyin +
        ", areaId=" + areaId +
        "}";
    }
}
