package com.camel.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx.account")
public class WxConstants {
    /**
     * 测试账号
     */
    private String appid = "wx0a2efc77aac2a84b";
    /**
     * 测试账号密钥
     */
    private String appsecret = "a0eb49319937944512d1fcf65b3216c0";
    /**
     * js安全域名
     * 不带http:// 或者https:// 以及项目名称
     */
    private String JS_SAVE_DOMAIN = "meedesidy.qicp.io";
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    // 二维码自定义参数事件
    public static final String QRCODE_EVENTKEY = "qrscene_";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
