package com.camel.interviewer.exceptions;

public class WxServerConnectException extends RuntimeException {
    public WxServerConnectException() {
        super("微信服务器连接错误");
    }
}
