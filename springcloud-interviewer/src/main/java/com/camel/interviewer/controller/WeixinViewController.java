package com.camel.interviewer.controller;

import com.camel.interviewer.annotation.AuthIgnore;
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
    public static final String APPID_VALUE = "wx40d4fe2c2c03a2f3";
    public static final String APPSECRET_VALUE = "c5f6da5b695d372321c9a46b792ec8f0";
    public static final String APPID_KEY = "appId";
    public static final String APPSECRET_KEY = "appsecret";
    public static final String TIMESTAM_KEY = "timestamp";
    public static final String NONCESTR_KEY = "nonceStr";
    public static final String SIGNATURE_KEY = "signature";
    public static final String JSAPILIST_KEY = "jsApiList";


    @AuthIgnore
    @GetMapping("/profile")
    public String profile(ModelMap map, HttpServletRequest request) {
        String timestamp = WxCommonsUtils.getInstance().timeStamp();
        String nonceStr = WxCommonsUtils.getInstance().getRandomStr();
        String token = WxTokenUtil.getInstance().getTocken(APPID_VALUE, APPSECRET_VALUE);
        Map<String, String> ticketMap = JsapiTicketUtil.getInstance().JsapiTicket(token);
        String str = "jsapi_ticket="+ticketMap.get("ticket")+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+"http://l27512n380.wicp.vip/viewer/profile";
        String signature = SHA1.encode(str);
        Map<String, String> wxConfig = new HashMap<>();
        wxConfig.put(APPID_KEY, APPID_VALUE);
        wxConfig.put(TIMESTAM_KEY, timestamp);
        wxConfig.put(NONCESTR_KEY, nonceStr);
        wxConfig.put(SIGNATURE_KEY, signature);
        map.put("wxConfig",wxConfig);
        return "profile";
    }
}
