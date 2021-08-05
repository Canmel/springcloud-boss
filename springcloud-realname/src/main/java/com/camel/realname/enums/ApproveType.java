package com.camel.realname.enums;

import com.camel.core.enums.MyEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApproveType implements MyEnum {

    企业(1,"企业"),
    外呼号码(2,"外呼号码"),
    个人(3,"个人");


    private Integer code;

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

    ApproveType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Serializable getValue() {
        return this.code;
    }
}
