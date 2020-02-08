package com.camel.sms.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class Md5 {
    public Md5() {
    }

    public static Md5 getInstance() {
        return new Md5();
    }

    public String encoderByMd5(String str) {
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            // 加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("UTF-8")));
        } catch (Exception e) {
            return "";
        }
    }
}
