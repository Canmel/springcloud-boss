package com.camel.system.enums;

import com.camel.core.enums.BaseEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NoticeTargetType implements BaseEnum {
    ROLE("角色", 1);
    private String description;
    private Integer code;
    private String column;

    NoticeTargetType(String description, Integer code) {
        this.description = description;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }

    public String getDescription() {
        return description;
    }

    public static List all(){
        List list = new ArrayList<>();
        for (NoticeTargetType type: NoticeTargetType.values()) {
            list.add(type.getValueMap());
        }
        return list;
    }

    public Map getValueMap() {
        Map map = new HashMap();
        map.put("name", this.getDescription());
        map.put("value", this.getCode());
        return map;
    }

    public BaseEnum getEnumByValue(Integer value) {
        return null;
    }
}
