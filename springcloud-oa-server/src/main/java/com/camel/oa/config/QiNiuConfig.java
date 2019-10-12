package com.camel.oa.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class QiNiuConfig {
    @Value("${qiniu.acount.access_key}")
    private String accessKey;

    @Value("${qiniu.acount.secret_key}")
    private String secretKey;

}
