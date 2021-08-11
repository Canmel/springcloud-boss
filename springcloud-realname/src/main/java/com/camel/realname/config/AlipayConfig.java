package com.camel.realname.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AlipayConfig {
    /**
     * 公司支付宝的APPID
     */
//    @Value("${alipay.app_id}")
    private String appId;
    /**
     * 公司支付宝商户私钥
     * 您的PKCS8格式RSA2私钥
     */
//    @Value("${alipay.merchat_private_key}")
    private String merchatPrivateKey;
    /**
     * 公司支付宝公钥
     * 对应APPID下的支付宝公钥
     */
//    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;
    /**
     *公司支付宝异步回调
     * 服务器一部通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，必须外网可以正常访问
     */
//    @Value("${alipay.notity_url}")
    private String notityUrl;
    /**
     *公司支付宝同步回调地址(如果是二维码扫描可以不配置)
     * 页面同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，必须外网可以正常访问
     */
//    @Value("${alipay.return_url}")
    private String returnUrl;
    /**
     *签名方式
     */
//    @Value("${alipay.sign_type}")
    private String signType;
    /**
     *字符串编码格式
     */
//    @Value("${alipay.charset}")
    private String charset;
    /**
     *支付宝网关
     */
//    @Value("${alipay.getway_url}")
    private String getwayUrl;
    /**
     *日志存放
     */
//    @Value("${alipay.log_path}")
    private String logPath;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchatPrivateKey() {
        return merchatPrivateKey;
    }

    public void setMerchatPrivateKey(String merchatPrivateKey) {
        this.merchatPrivateKey = merchatPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getNotityUrl() {
        return notityUrl;
    }

    public void setNotityUrl(String notityUrl) {
        this.notityUrl = notityUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getGetwayUrl() {
        return getwayUrl;
    }

    public void setGetwayUrl(String getwayUrl) {
        this.getwayUrl = getwayUrl;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
}
