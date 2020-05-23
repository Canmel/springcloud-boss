package com.camel.survey.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsAccessState implements MyEnum {
    NORMAL(0, "您预约的班次日期尚未开始"), FAILD(1, "超时，或者班次时间提前超过半个小时"), SUCESS(2, "认证成功");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    ZsAccessState(Integer code, String name) {
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
        return this.code;
    }

}
