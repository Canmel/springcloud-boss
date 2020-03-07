package com.camel.interviewer.utils;

import java.util.UUID;

public class WxCommonsUtils {
    public WxCommonsUtils() {
    }

    public static WxCommonsUtils getInstance() {
        return new WxCommonsUtils();
    }

    public String getRandomStr() {
        return UUID.randomUUID().toString();
    }

    public String timeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
