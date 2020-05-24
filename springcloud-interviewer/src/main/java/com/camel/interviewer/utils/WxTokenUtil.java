package com.camel.interviewer.utils;

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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WxTokenUtil {

    public static final String WX_ACCESS_TOKEN_RKEY = "wx_access_token";

    public static WxTokenUtil getInstance() {
        return new WxTokenUtil();
    }

    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

    public WxTokenUtil() {
    }

    public String getTocken(String appId, String appSecret, RedisTemplate redisTemplate) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        HashMap<String, String> token = (HashMap<String, String>) operations.get(WX_ACCESS_TOKEN_RKEY);
        if(!ObjectUtils.isEmpty(token) && token.containsKey("access_token")) {
            return token.get("access_token");
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
            operations.set(WX_ACCESS_TOKEN_RKEY, map, 7200, TimeUnit.SECONDS);
            result = accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }
}
