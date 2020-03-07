package com.camel.interviewer.utils;

import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.Map;

public class JsapiTicketUtil {
    public JsapiTicketUtil() {
    }

    public static JsapiTicketUtil getInstance() {
        return new JsapiTicketUtil();
    }

    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public Map<String, String> JsapiTicket(String accessToken) {
        String requestUrl = GetPageAccessTokenUrl.replace("ACCESS_TOKEN", accessToken);
        HttpClient client = null;
        Map<String, String> result = new HashMap<String, String>();
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
            result.put("errcode", errcode);
            result.put("errmsg", errmsg);
            result.put("ticket", ticket);
            result.put("expires_in", expires_in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }
}
