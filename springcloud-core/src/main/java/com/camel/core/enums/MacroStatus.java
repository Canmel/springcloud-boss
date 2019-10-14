package com.camel.core.enums;

import com.camel.core.enums.BaseEnum;


/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'      字典状态
 *              '::::::::::::'
 *                .::::::::::
 *           '::::::::::::::..
 *                ..::::::::::::.
 *              ``::::::::::::::::
 *               ::::``:::::::::'        .:::.
 *              ::::'   ':::::'       .::::::::.
 *            .::::'      ::::     .:::::::'::::.
 *           .:::'       :::::  .:::::::::' ':::::.
 *          .::'        :::::.:::::::::'      ':::::.
 *         .::'         ::::::::::::::'         ``::::.
 *     ...:::           ::::::::::::'              ``::.
 *    ```` ':.          ':::::::::'                  ::::..
 *                       '.:::::'                    ':'````..
 * @author baily 
 * @since 2019/7/7
 **/
public enum MacroStatus implements BaseEnum {
    /**/
    NORMAL("正常", 1), INVALID("无效", 0);
    /**
     备注
     */
    private String description;
    /**
     数据代码
     */
    private Integer code;
    /**
     数据列名
     */
    private String column;

    public String getDescription() {
        return description;
    }

    public Integer getCode() {
        return code;
    }

    public String getColumn() {
        return column;
    }

    MacroStatus(String description, Integer code) {
        this.description = description;
        this.code = code;
        this.column = "status";
    }

    @Override
    public Integer getValue() {
        return getCode();
    }

    public BaseEnum getEnumByValue(Integer value) {
        return null;
    }

    @Override
    public String toString() {
        return "MacroStatus{" +
                "description='" + description + '\'' +
                ", code=" + code +
                ", column='" + column + '\'' +
                '}';
    }


}
