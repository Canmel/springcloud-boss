package com.camel.oa.enums;

import com.baomidou.mybatisplus.enums.IEnum;
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
 * <是否枚举类型>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZsYesOrNo implements IEnum {
    /**
     * 状态
     */
    INVALID("否", 0),
    NORMAL("是", 1);


    private String name;
    private Integer value;

    ZsYesOrNo(String name, Integer value) {
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
        for (ZsYesOrNo status : ZsYesOrNo.values()) {
            list.add(status.getValueMap());
        }
        return list;
    }

    public static ZsYesOrNo getEnumByValue(Integer value) {
        for (ZsYesOrNo status : ZsYesOrNo.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
