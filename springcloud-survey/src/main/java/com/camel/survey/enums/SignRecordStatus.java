package com.camel.survey.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

public enum SignRecordStatus implements IEnum {
    INVALID("无效", 0), NORMAL("正常", 1);

    SignRecordStatus(String name, Integer code) {
        this.code = code;
        this.name = name;
    }

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

    @Override
    public Serializable getValue() {
        return this.code;
    }
}
