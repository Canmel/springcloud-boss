package com.camel.interviewer.utils;

import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JsapiTicketUtil {

    public static final String WX_JS_TICKET_RKEY = "wx_jsapi_ticket";
    public JsapiTicketUtil() {
    }

    public static JsapiTicketUtil getInstance() {
        return new JsapiTicketUtil();
    }

    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public String JsapiTicket(String accessToken, RedisTemplate redisTemplate) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        String obj = (String) operations.get(WX_JS_TICKET_RKEY);
        if(StringUtils.isNotBlank(obj)) {
            return obj;
        }
        String requestUrl = GetPageAccessTokenUrl.replace("ACCESS_TOKEN", accessToken);
        HttpClient client = null;
        String result = null;
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject OpenidJSONO = JSONObject.fromObject(response);
            String errcode = String.valueOf(OpenidJSONO.get("errcode"));
            String errmsg = String.valueOf(OpenidJSONO.get("errmsg"));
            String ticket = String.valueOf(OpenidJSONO.get("ticket"));
            String expires_in = String.valueOf(OpenidJSONO.get("expires_in"));
            result = ticket;
            if("ok".equals(errmsg) && "0".equals(errcode)) {
                operations.set(WX_JS_TICKET_RKEY, ticket, Long.parseLong(expires_in), TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }
}
