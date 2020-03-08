package com.camel.interviewer.controller;

import com.camel.interviewer.annotation.AuthIgnore;
import com.camel.interviewer.config.WxConstants;
import com.camel.interviewer.utils.JsapiTicketUtil;
import com.camel.interviewer.utils.SHA1;
import com.camel.interviewer.utils.WxCommonsUtils;
import com.camel.interviewer.utils.WxTokenUtil;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        System.out.println("------");
        return "profile";
    }
}
