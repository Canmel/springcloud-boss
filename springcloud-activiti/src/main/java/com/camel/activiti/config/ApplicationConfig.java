package com.camel.activiti.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationConfig {

    @Value("${boss.getway.url}")
    private String getWayUrl;

    @Value("${boss.getway.port}")
    private String getWayPort;
}
