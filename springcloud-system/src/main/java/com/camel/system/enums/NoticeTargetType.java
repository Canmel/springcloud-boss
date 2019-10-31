package com.camel.system.enums;

import com.camel.core.enums.BaseEnum;

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
 * <告警目标类型>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
public enum NoticeTargetType implements BaseEnum {
    /**
     * 角色
     */
    ROLE("角色", 1);
    /**
     * 描述
     */
    private String description;
    /**
     * 编码
     */
    private Integer code;
    /**
     * 数据库列名
     */
    private String column;

    NoticeTargetType(String description, Integer code) {
        this.description = description;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }

    public String getDescription() {
        return description;
    }

    public static List all(){
        List list = new ArrayList<>();
        for (NoticeTargetType type: NoticeTargetType.values()) {
            list.add(type.getValueMap());
        }
        return list;
    }

    public Map getValueMap() {
        Map map = new HashMap();
        map.put("name", this.getDescription());
        map.put("value", this.getCode());
        return map;
    }

    /**
     * 根据value获取枚举类型
     * @param value
     * @return
     */
    public BaseEnum getEnumByValue(Integer value) {
        return null;
    }
}
