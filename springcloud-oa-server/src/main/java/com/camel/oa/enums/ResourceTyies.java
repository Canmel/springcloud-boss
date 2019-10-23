package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

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
public enum ResourceTyies implements IEnum {
    /* 多个类型 */
    NORMAL("土地", 1), WORKSHOP("厂房", 2), OFFICE_BUILDING("写字楼", 3), SHOPS("商铺", 4), PARK("园区", 5), SYNTHESIS("综合体", 6);

    private String name;
    private Integer value;

    ResourceTyies(String name, Integer value) {
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
        for (ResourceTyies tyies : ResourceTyies.values()) {
            list.add(tyies.getValueMap());
        }
        return list;
    }

    public static ResourceTyies getEnumByValue(Integer value) {
        for (ResourceTyies tyies : ResourceTyies.values()) {
            if (tyies.getValue() == value) {
                return tyies;
            }
        }
        return null;
    }
}
