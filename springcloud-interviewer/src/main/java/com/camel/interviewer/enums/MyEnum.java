package com.camel.interviewer.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Optional;

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
 * <项目状态>
 * @author baily
 * @since 1.0
 * @date 2019/12/6
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface MyEnum<T> extends IEnum {

    static <T> MyEnum valueOfEnum(Class<MyEnum> enumClass, T value) {
        if (value == null) {
            return null;
        }
        MyEnum[] enums = enumClass.getEnumConstants();
        Optional<MyEnum> optional = Arrays.asList(enums).stream().filter(baseEnum -> baseEnum.getValue().equals(value)).findAny();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("未找到：" + value + "对应的" + enumClass.getName());
    }

}
