package com.camel.sms.service;

public interface WxNoticeService {
    /**
     * 给指定微信用户发送消息
     * @param openid 微信openid
     * @param msg 消息主体
     */
    void send(String openid, String msg);
}
