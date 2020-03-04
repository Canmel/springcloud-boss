package com.camel.interviewer.convertor;

import com.camel.interviewer.enums.MyEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.Serializable;
import java.util.HashMap;
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
 * <Integer 转换 枚举类型>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
public class IntegerToEnumConverterFactory implements ConverterFactory<Serializable, MyEnum> {
    private static final Map<Class, Converter> CONVERTER_MAP =  new HashMap<>();

    @Override
    public <T extends MyEnum> Converter<Serializable, T> getConverter(Class<T> targetType) {
        Converter<Serializable, T> converter = CONVERTER_MAP.get(targetType);
        if(converter == null) {
            converter = new IntegerToEnumConverter<>(targetType);
            CONVERTER_MAP.put(targetType, converter);
        }
        return converter;
    }

    class IntegerToEnumConverter<T extends MyEnum> implements Converter<Serializable, T> {

        private Map<Serializable, T> enumMap = new HashMap<>();

        IntegerToEnumConverter(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(e.getValue(), e);
            }
        }

        @Override
        public T convert(Serializable source) {

            T t = enumMap.get(source);
            if (t == null) {
                // 异常可以稍后去捕获
                throw new IllegalArgumentException("No element matches " + source);
            }
            return t;
        }
    }
}
