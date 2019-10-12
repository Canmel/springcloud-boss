package com.camel.core.enums;

import com.camel.core.enums.BaseEnum;

/** @author baily */
public enum RoleStatus implements BaseEnum {
    /**/
    NORMAL("1", "正常"), UNVALID("0", "无效");

    private String code;
    private String desc;

    RoleStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return Integer.parseInt(getCode());
    }
}
