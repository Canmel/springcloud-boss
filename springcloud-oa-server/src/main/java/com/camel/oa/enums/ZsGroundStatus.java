package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsGroundStatus implements IEnum {
    INVALID("无效", 0),
    NORMAL("正常", 1);


    private String name;
    private Integer value;

    ZsGroundStatus(String name, Integer value) {
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
        for (ZsGroundStatus status : ZsGroundStatus.values()) {
            list.add(status.getValueMap());
        }
        return list;
    }

    public static ZsGroundStatus getEnumByValue(Integer value) {
        for (ZsGroundStatus status : ZsGroundStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}
