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
public enum ZsCommentTyies implements IEnum {
    /* 多个类型 */
    COMMENT("评论", 0), REPLY("回复", 1);

    private String name;
    private Integer value;

    ZsCommentTyies(String name, Integer value) {
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
        for (ZsCommentTyies tyies : ZsCommentTyies.values()) {
            list.add(tyies.getValueMap());
        }
        return list;
    }

    public static ZsCommentTyies getEnumByValue(Integer value) {
        for (ZsCommentTyies tyies : ZsCommentTyies.values()) {
            if (tyies.getValue().equals(value)) {
                return tyies;
            }
        }
        return null;
    }
}
