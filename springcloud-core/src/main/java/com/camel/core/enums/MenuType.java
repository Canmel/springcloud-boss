package com.camel.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author baily*/
public enum MenuType implements BaseEnum {
    /**/
    TOP(0, "一级菜单"), SUB(1, "二级菜单");

    private Integer code;

    private String description;

    private String column;

    MenuType(Integer code, String description) {
        this.code = code;
        this.description = description;
        this.column = "type";
    }

    MenuType() {
    }

    public String getColumn() {
        return column;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Map getValueMap() {
        Map map = new HashMap();
        map.put("name", this.getDescription());
        map.put("value", this.getCode());
        return map;
    }

    public static List all(){
        List list = new ArrayList<>();
        for (MenuType type: MenuType.values()) {
            list.add(type.getValueMap());
        }
        return list;
    }


    @Override
    public Integer getValue() {
        return getCode();
    }

    public BaseEnum getEnumByValue(Integer value) {
        return null;
    }


}
