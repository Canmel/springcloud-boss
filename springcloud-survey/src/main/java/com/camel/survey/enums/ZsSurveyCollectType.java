package com.camel.survey.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <问卷收集方式>
 * @author baily
 * @since 1.0
 * @date 2019/12/6
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsSurveyCollectType implements MyEnum {
    SMS(0, "短信"), PHONE(1, "电话"), INTERVIEW(2, "走访");

    private String name;
    private Integer value;

    ZsSurveyCollectType(Integer value, String name) {
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
        for (ZsSurveyCollectType reimbursementStatus : ZsSurveyCollectType.values()) {
            list.add(reimbursementStatus.getValueMap());
        }
        return list;
    }

    public static ZsSurveyCollectType getEnumByValue(Integer value) {
        for (ZsSurveyCollectType resourceStatus : ZsSurveyCollectType.values()) {
            if (resourceStatus.getValue().equals(value)) {
                return resourceStatus;
            }
        }
        return null;
    }
}
