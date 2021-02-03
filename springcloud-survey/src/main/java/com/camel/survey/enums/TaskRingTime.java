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
 *     最大振铃时长
 * @author baily
 * @since 1.0
 * @date 2019/12/6
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TaskRingTime implements MyEnum {
    RING_TIME_30(30, "30秒"),
    RING_TIME_35(35, "35秒"),
    RING_TIME_40(40, "40秒"),
    RING_TIME_45(45, "45秒"),
    RING_TIME_50(50, "50秒"),
    RING_TIME_55(55, "55秒"),
    RING_TIME_60(60, "60秒");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    TaskRingTime(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TaskRingTime getEnum(String name) {
        for (TaskRingTime enums : TaskRingTime.values()) {
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

    public static TaskRingTime getEnumByValue(Integer value) {
        for (TaskRingTime enums : TaskRingTime.values()) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        return null;
    }
}
