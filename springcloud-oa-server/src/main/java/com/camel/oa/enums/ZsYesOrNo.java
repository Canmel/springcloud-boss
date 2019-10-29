package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'      文档状态
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 *
 * @author baily
 * @since 2019/7/8
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsYesOrNo implements IEnum {
    OK("是", 1),
    NO("否", 0);


    private String name;
    private Integer value;

    ZsYesOrNo(String name, Integer value) {
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
        for (ZsYesOrNo status : ZsYesOrNo.values()) {
            list.add(status.getValueMap());
        }
        return list;
    }

    public static ZsYesOrNo getEnumByValue(Integer value) {
        for (ZsYesOrNo status : ZsYesOrNo.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}
