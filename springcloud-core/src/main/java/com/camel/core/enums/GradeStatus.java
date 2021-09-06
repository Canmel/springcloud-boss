package com.camel.core.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * @author 259
 * @version 1.0
 * @date 2021/6/22
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GradeStatus implements MyEnum {
    GRADE_00(0, "其它"),
    GRADE_01(1, "高级"),
    GRADE_02(2, "中级"),
    GRADE_03(3, "初级"),
    GRADE_04(4, "见习");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    GradeStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    public static GradeStatus getEnumByValue(Integer value) {
        for (GradeStatus enums : GradeStatus.values()) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}
