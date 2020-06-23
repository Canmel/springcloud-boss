package com.camel.survey.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

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
 * <工作记录来源>
 * @author baily
 * @since 1.0
 * @date 2019/12/6
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsWorkSource implements MyEnum {
    CHECKED(0, "系统结算");

    private String name;
    private Integer value;

    ZsWorkSource(Integer value, String name) {
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
        for (ZsWorkSource reimbursementStatus : ZsWorkSource.values()) {
            list.add(reimbursementStatus.getValueMap());
        }
        return list;
    }

    public static ZsWorkSource getEnumByValue(Integer value) {
        for (ZsWorkSource resourceStatus : ZsWorkSource.values()) {
            if (resourceStatus.getValue().equals(value)) {
                return resourceStatus;
            }
        }
        return null;
    }
}
