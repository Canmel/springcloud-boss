package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsProjectIndustryTypies implements IEnum {
    /* 多个类型 */
    ONE("一产", 0), TWO("二产", 1), THREE("三产", 2);

    private String name;
    private Integer value;

    ZsProjectIndustryTypies(String name, Integer value) {
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
        for (ZsProjectIndustryTypies levels : ZsProjectIndustryTypies.values()) {
            list.add(levels.getValueMap());
        }
        return list;
    }

    public static ZsProjectIndustryTypies getEnumByValue(Integer value) {
        for (ZsProjectIndustryTypies levels : ZsProjectIndustryTypies.values()) {
            if (levels.getValue() == value) {
                return levels;
            }
        }
        return null;
    }
}
