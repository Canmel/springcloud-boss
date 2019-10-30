package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsProjectTypies implements IEnum {
    /* 多个类型 */
    GUIDE("盯引项目", 0), RECOMMEND("推介项目", 1), BUILDING("在建项目", 2), CONFIRM("落地项目", 3);

    private String name;
    private Integer value;

    ZsProjectTypies(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    //    @JsonValue 直接返回name
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public Map getValueMap() {
        Map map = new HashMap();
        map.put("name", this.getName());
        map.put("value", this.getValue());
        return map;
    }

    public static List all() {
        List list = new ArrayList<>();
        for (ZsProjectTypies levels : ZsProjectTypies.values()) {
            list.add(levels.getValueMap());
        }
        return list;
    }

    public static ZsProjectTypies getEnumByValue(Integer value) {
        for (ZsProjectTypies levels : ZsProjectTypies.values()) {
            if (levels.getValue() == value) {
                return levels;
            }
        }
        return null;
    }
}
