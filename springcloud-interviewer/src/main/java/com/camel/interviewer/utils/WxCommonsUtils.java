package com.camel.interviewer.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class WxCommonsUtils {
    public WxCommonsUtils() {
    }

    public static WxCommonsUtils getInstance() {
        return new WxCommonsUtils();
    }

    public String getRandomStr() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    public String timeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
