package com.camel.survey.config;

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
     * 商户ID
     */
    private String mchid = "1511764991";

    /**
     * 商户名称
     */
    private String mchname = "赛炜大数据服务有限公司";

    /**
     * 微信商户秘钥
     */
    private String appkey = "d4ahjqulmuvuzyna2lj00nv1a40ww54u";


    /**
     * js安全域名
     * 不带http:// 或者https:// 以及项目名称
     */
    private String JS_SAVE_DOMAIN = "2fd59a5d.ngrok.io";
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

}
