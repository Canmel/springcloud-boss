package com.camel.survey.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

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
 * <状态>
 *     命中率
 * @author baily
 * @since 1.0
 * @date 2019/12/6
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TaskHitRate implements MyEnum {
    AUTO(0, "自动计算"),
    PERCENT_30(1, "30%命中率"),
    PERCENT_40(2, "40%命中率"),
    PERCENT_50(3, "50%命中率"),
    PERCENT_60(4, "60%命中率"),
    PERCENT_70(5, "70%命中率"),
    PERCENT_80(6, "80%命中率"),
    PERCENT_90(7, "90%命中率"),
    PERCENT_100(8, "100%命中率");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    TaskHitRate(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TaskHitRate getEnum(String name) {
        for (TaskHitRate enums : TaskHitRate.values()) {
            if (enums.getName().equals(name)) {
                return enums;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public Serializable getValue() {
        return this.code;
    }

    public static TaskHitRate getEnumByValue(Integer value) {
        for (TaskHitRate enums : TaskHitRate.values()) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        return null;
    }
}
