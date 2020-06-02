package com.camel.interviewer.exceptions;

public class NotWxExplorerException extends RuntimeException {
    public NotWxExplorerException() {
        super("请使用微信客户端浏览");
    }
}
