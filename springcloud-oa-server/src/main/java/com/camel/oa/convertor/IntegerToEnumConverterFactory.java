package com.camel.oa.convertor;

import com.baomidou.mybatisplus.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class IntegerToEnumConverterFactory implements ConverterFactory<Serializable, IEnum> {
    private static final Map<Class, Converter> converterMap =  new HashMap<>();

    @Override
    public <T extends IEnum> Converter<Serializable, T> getConverter(Class<T> targetType) {
        Converter<Serializable, T> converter = converterMap.get(targetType);
        if(converter == null) {
            converter = new IntegerToEnumConverter<>(targetType);
            converterMap.put(targetType, converter);
        }
        return converter;
    }

    class IntegerToEnumConverter<T extends IEnum> implements Converter<Serializable, T> {

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
