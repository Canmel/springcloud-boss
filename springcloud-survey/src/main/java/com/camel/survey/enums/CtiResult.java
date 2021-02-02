package com.camel.survey.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * ___====-_  _-====___
 * _--^^^#####//      \\#####^^^--_
 * _-^##########// (    ) \\##########^-_
 * -############//  |\^^/|  \\############-
 * _/############//   (@::@)   \\############\_
 * /#############((     \\//     ))#############\
 * -###############\\    (oo)    //###############-
 * -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 * _#/|##########/\######(   /\   )######/\##########|\#_
 * |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 * `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 * `   `  `      `   / | |  | | \   '      '  '   '
 * (  | |  | |  )
 * __\ | |  | | /__
 * (vvv(VVV)(VVV)vvv)
 * <状态>
 *
 * @author baily
 * @date 2019/12/6
 * @since 1.0
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CtiResult {
    SUCCESS("000000", "成功"),
    PARAMS_REQUIRE("000001", "必选参数不匹配"),
    PARAMS_ERROR("000002", "参数内容不合法"),
    IDENTITY_ERROR("000003", "身份认证失败"),
    SYSTEM_ERROR("999999", "系统异常");

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态名称
     */
    private String name;

    CtiResult(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
