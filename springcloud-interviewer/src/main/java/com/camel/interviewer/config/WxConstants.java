package com.camel.interviewer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wx.account")
public class WxConstants {
    /**
     * 测试账号
     */
    private String appid = "wx40d4fe2c2c03a2f3";
    /**
     * 测试账号密钥
     */
    private String appsecret = "c5f6da5b695d372321c9a46b792ec8f0";
    /**
     * js安全域名
     * 不带http:// 或者https:// 以及项目名称
     */
    private String JS_SAVE_DOMAIN = "2fd59a5d.ngrok.io";
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

}
