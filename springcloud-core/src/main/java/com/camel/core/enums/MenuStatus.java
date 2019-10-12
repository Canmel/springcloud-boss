package com.camel.core.enums;

import com.camel.core.enums.BaseEnum;

/** @author baily */
public enum MenuStatus implements BaseEnum {
    /**/
    NORMAL("正常", 1), INVALID("无效", 0);
    private String description;
    private Integer code;
    private String column;

    public String getDescription() {
        return description;
    }

    public Integer getCode() {
        return code;
    }

    public String getColumn() {
        return column;
    }

    MenuStatus(String description, Integer code) {
        this.description = description;
        this.code = code;
        this.column = "status";
    }


    @Override
    public Integer getValue() {
        return getCode();
    }
}
