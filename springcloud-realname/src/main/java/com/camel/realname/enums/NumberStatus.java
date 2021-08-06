package com.camel.realname.enums;

import com.camel.core.enums.MyEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NumberStatus implements MyEnum {

    DELETED(0, "已删除"),EDITABLE(1, "创建"), APPLYING(2, "申请中"), SUCCESS(3, "成功"), FAILD(4, "失败");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    NumberStatus(Integer code, String name) {
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

    public static NumberStatus getEnumByCode(Integer code) {
        for (NumberStatus enums : NumberStatus.values()) {
            if (enums.getCode().equals(code)) {
                return enums;
            }
        }
        return null;
    }
}
