package com.camel.auth.config.oauth;

import lombok.Data;

@Data
public class CaptchaDTO {
    private String captchaBase64;
    private String key;
}
