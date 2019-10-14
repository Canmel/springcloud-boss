package com.camel.oa.enums;

import com.camel.core.enums.BaseEnum;
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
public enum ResourceTyies implements BaseEnum {
    /* 多个类型 */
    NORMAL("土地", 1), INVALID("厂房", 2), OFFICE_BUILDING("写字楼", 3), SHOPS("商铺", 2), PARK("园区", 2), SYNTHESIS("综合体", 2);


    private String name;
    private Integer value;

    ResourceTyies(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

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
        for (ResourceTyies reimbursementStatus : ResourceTyies.values()) {
            list.add(reimbursementStatus.getValueMap());
        }
        return list;
    }

    public static ResourceTyies getEnumByValue(Integer value) {
        for (ResourceTyies resourceTyies : ResourceTyies.values()) {
            if (resourceTyies.getValue() == value) {
                return resourceTyies;
            }
        }
        return null;
    }
}
