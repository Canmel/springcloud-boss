package com.camel.interviewer.controller;

import com.camel.interviewer.annotation.AuthIgnore;
import com.camel.interviewer.config.WxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view")
@Controller
public class WeixinViewController {
    @Autowired
    private WxConstants wxConstants;

    public static final String APPID_KEY = "appId";
    public static final String APPSECRET_KEY = "appsecret";
    public static final String TIMESTAM_KEY = "timestamp";
    public static final String NONCESTR_KEY = "nonceStr";
    public static final String SIGNATURE_KEY = "signature";
    public static final String JSAPILIST_KEY = "jsApiList";


    @AuthIgnore
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @AuthIgnore
    @GetMapping("/salary")
    public String salary() {
        return "salary";
    }

    @AuthIgnore
    @GetMapping("/appointment")
    public String appointment() {
        return "appointment";
    }
}
