package com.camel.interviewer.model;

import lombok.Data;

@Data
public class WxJsConfig {
    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;

    public WxJsConfig(String appId, String timestamp, String nonceStr, String signature) {
        this.appId = appId;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signature = signature;
    }

    public WxJsConfig() {
    }
}
