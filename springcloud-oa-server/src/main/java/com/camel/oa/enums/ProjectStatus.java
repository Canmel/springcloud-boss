package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
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
public enum ProjectStatus implements IEnum {
    /**
     * 无效状态
     */
    INVALID("无效", 0),
    /**
     * 正常状态
     */
    NORMAL("正常", 1),
    /**
     * 部署状态
     */
    RELEASE("部署", 2);


    private String name;
    private Integer value;

    ProjectStatus(String name, Integer value) {
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
        for (ProjectStatus reimbursementStatus : ProjectStatus.values()) {
            list.add(reimbursementStatus.getValueMap());
        }
        return list;
    }

    public static ProjectStatus getEnumByValue(Integer value) {
        for (ProjectStatus resourceStatus : ProjectStatus.values()) {
            if (resourceStatus.getValue().equals(value)) {
                return resourceStatus;
            }
        }
        return null;
    }
}
