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
public enum ZsProjectLevels implements IEnum {
    /* 多个类型 */
    PL_ONE("一级", 0), PL_TWO("二级", 1), PL_THREE("三级", 2), PL_FOUR("四级", 3);

    private String name;
    private Integer value;

    ZsProjectLevels(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

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
        for (ZsProjectLevels levels : ZsProjectLevels.values()) {
            list.add(levels.getValueMap());
        }
        return list;
    }

    public static ZsProjectLevels getEnumByValue(Integer value) {
        for (ZsProjectLevels levels : ZsProjectLevels.values()) {
            if (levels.getValue().equals(value)) {
                return levels;
            }
        }
        return null;
    }
}
