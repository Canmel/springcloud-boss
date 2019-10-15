package com.camel.oa.enums.convertor;

import com.camel.core.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

public class IntegerToEnumConverterFactory implements ConverterFactory<Integer, BaseEnum> {
    private static final Map<Class, Converter> converterMap =  new HashMap<>();

    @Override
    public <T extends BaseEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
        Converter<Integer, T> converter = converterMap.get(targetType);
        if(converter == null) {
            converter = new IntegerToEnumConverter<>(targetType);
            converterMap.put(targetType, converter);
        }
        return converter;
    }

    class IntegerToEnumConverter<T extends BaseEnum> implements Converter<Integer, T> {

        private Map<Integer, T> enumMap = new HashMap<>();

        IntegerToEnumConverter(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(e.getValue(), e);
            }
        }

        @Override
        public T convert(Integer source) {

            T t = enumMap.get(source);
            if (t == null) {
                // 异常可以稍后去捕获
                throw new IllegalArgumentException("No element matches " + source);
            }
            return t;
        }
    }
}
