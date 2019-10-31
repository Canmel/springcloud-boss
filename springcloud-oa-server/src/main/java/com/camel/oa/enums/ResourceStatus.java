package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.camel.core.enums.BaseEnum;
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
public enum ResourceStatus implements IEnum {
    /**/
    NORMAL("正常", 1), INVALID("无效", 0);


    private String name;
    private Integer value;

    ResourceStatus(String name, Integer value) {
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
        for (ResourceStatus reimbursementStatus : ResourceStatus.values()) {
            list.add(reimbursementStatus.getValueMap());
        }
        return list;
    }

    public static ResourceStatus getEnumByValue(Integer value) {
        for (ResourceStatus resourceStatus : ResourceStatus.values()) {
            if(resourceStatus.getValue().equals(value)) {
                return resourceStatus;
            }
        }
        return null;
    }
}
