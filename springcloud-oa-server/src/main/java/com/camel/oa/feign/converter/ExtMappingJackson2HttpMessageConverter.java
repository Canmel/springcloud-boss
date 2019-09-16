package com.camel.oa.feign.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ExtMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public ExtMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        setSupportedMediaTypes(mediaTypes);
    }
}
