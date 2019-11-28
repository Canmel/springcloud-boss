package com.camel.attendance.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

public enum SignRecordType implements IEnum {
    SIGN_IN("签到", 0), SIGN_OUT("签退", 1);

    SignRecordType(String name, Integer code) {
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
