package com.camel.sms.exceptions;

public class NoTargetSmsException extends RuntimeException {
    public NoTargetSmsException() {
        super("未配置相关号码");
    }
}
