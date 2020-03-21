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
 * @author baily
 * @since 1.0
 * @date 2019/12/6
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsApply implements MyEnum {
    NORMAL(0, "正常"), APPLY(1, "发起申请"), SUCESS(2, "申请成功"), FAILD(3, "申请失败");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    ZsApply(Integer code, String name) {
        this.code = code;
        this.name = name;
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

    public static ZsApply getEnumByValue(Integer value) {
        for (ZsApply enums : ZsApply.values()) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        return null;
    }
}
