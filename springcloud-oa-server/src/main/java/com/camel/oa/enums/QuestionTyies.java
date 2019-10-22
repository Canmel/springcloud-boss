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
public enum QuestionTyies implements IEnum {
    
    SINGLCHOICE("单选", 0), MULTIPLECHOICE("多选", 1), OTHER("其他", 2);

    private String name;
    private Integer value;

    QuestionTyies(String name, Integer value) {
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
        for (QuestionTyies questionTyies : QuestionTyies.values()) {
            list.add(questionTyies.getValueMap());
        }
        return list;
    }

    public static QuestionTyies getEnumByValue(Integer value) {
        for (QuestionTyies questionTyies : QuestionTyies.values()) {
            if (questionTyies.getValue() == value) {
                return questionTyies;
            }
        }
        return null;
    }
}
