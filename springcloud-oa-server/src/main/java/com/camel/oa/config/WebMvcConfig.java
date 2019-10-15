package com.camel.oa.config;

import com.camel.oa.enums.convertor.IntegerToEnumConverterFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 枚举类的转换器 addConverterFactory
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        System.out.println("问问成水何惧");
        registry.addConverterFactory(new IntegerToEnumConverterFactory());
    }
}
