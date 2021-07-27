package com.camel.realname.enums;

import com.camel.core.enums.MyEnum;
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
 * <审核状态>
 * @author baily
 * @since 1.0
 * @date 2019/12/6
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsApplyStatus implements MyEnum {
    CREATED(0, "创建"), APPLYING(1, "审核中"), APPLY_SUCCESS(2, "审核成功"), APPLY_FAILD(3, "审核被拒");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    ZsApplyStatus(Integer code, String name) {
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

    public static ZsApplyStatus getEnumByValue(Integer value) {
        for (ZsApplyStatus enums : ZsApplyStatus.values()) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        return null;
    }
}
