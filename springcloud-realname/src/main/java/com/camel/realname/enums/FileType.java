package com.camel.realname.enums;

import com.camel.core.enums.MyEnum;

import java.io.Serializable;

public enum FileType implements MyEnum {
    APPLYING(1, "客户申请表"), LISENCE(2, "资质"), CARDLEGAL(3, "法人身份证");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    FileType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

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
        return null;
    }
}
