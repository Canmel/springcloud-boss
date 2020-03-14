package com.camel.sms.enums;

public enum SmsStatus {
    UNKOWNE("-1", "未知错误"),
    SUCCESS("0", "发送成功"),
    NO_BALANCE("41", "余额不足"),
    ACCOUNT_NOT_EXIST("40", "账号不存在"),
    NOT_VALID_IP("43", "IP地址限制"),
    SENSITIVE_CONTENT("50", "内容含有敏感词"),
    NOT_VALID_PHONE_NO("51", "手机号码不正确"),
    ;
    private String code;
    private String text;

    SmsStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
