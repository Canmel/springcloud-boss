package com.camel.survey.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Configurable
@ConfigurationProperties(prefix = "survey.price")
public class SurveyPriceConfig {

    @Getter
    @Setter
    private Integer phone;
}
