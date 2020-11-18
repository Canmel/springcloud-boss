package com.camel.interviewer.utils;

import cn.hutool.core.date.DateUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WxTokenUtil {

    public static String WX_TOKEN;

    public static Date WX_TOKEN_DATE;

    public static final String WX_ACCESS_TOKEN_RKEY = "wx_access_token";

    public static WxTokenUtil getInstance() {
        return new WxTokenUtil();
    }

    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

    public WxTokenUtil() {
    }

    public String getTocken(String appId, String appSecret, RedisTemplate redisTemplate) {
        System.out.println("appID = " + appId + "  appSecret = " + appSecret);

        System.out.println("------------ttttttoooookkkkkkennnn" + WX_TOKEN);
        if(!ObjectUtils.isEmpty(WX_TOKEN) && new Date().getTime() > WX_TOKEN_DATE.getTime() && DateUtil.nanosToSeconds(new Date().getTime() - WX_TOKEN_DATE.getTime()) > 7200) {
            System.out.println(WX_TOKEN);
            return WX_TOKEN;
        }
        String requestUrl = GetPageAccessTokenUrl.replace("APPID", appId).replace("SECRET", appSecret);
        HttpClient client = null;
        String result = null;
        String accessToken = null;
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            Map<String, Object> map = new GsonJsonParser().parseMap(response);
            accessToken = String.valueOf(map.get("access_token"));
            WX_TOKEN = accessToken;
            WX_TOKEN_DATE = new Date();
            result = accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }
}
