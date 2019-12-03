package com.camel.attendance.enums;

import com.baomidou.mybatisplus.enums.IEnum;

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
 * <>
 * @author baily
 * @since 1.0
 * @date 2019/11/28
 **/
public enum SignRecordDetermine implements IEnum {
    DELAY("迟到", 0), NORMAL("正常", 1), ADVANCE("早退", 2), FORGET("未打卡", 3);

    SignRecordDetermine(String name, Integer code) {
        this.code = code;
        this.name = name;
    }

    /**
     * 代码
     */
    private Integer code;

    /**
     * 名称
     */
    private String name;

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
    public Integer getValue() {
        return this.code;
    }

    public static SignRecordDetermine getEnumByValue(Integer value) {
        for (SignRecordDetermine resourceStatus : SignRecordDetermine.values()) {
            if (resourceStatus.getValue().equals(value)) {
                return resourceStatus;
            }
        }
        return null;
    }
}
