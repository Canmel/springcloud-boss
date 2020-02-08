package com.camel.sms.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import sun.misc.BASE64Encoder;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <短信工具>
 * @author baily
 * @since 1.0
 * @date 2020/2/8
 **/
public class LhzxHttpClientUtil {
    private static LhzxHttpClientUtil instance = new LhzxHttpClientUtil();

    private LhzxHttpClientUtil() {
    }

    public static LhzxHttpClientUtil getInstance() {
        return instance;
    }

    /**
     * 返回客户进行POST方式调用的结果
     *
     * @param jsonRequestString json格式的字符串
     * @param restServiceURL    服务地址
     * @return String  json格式返回
     */
    private String sendPostRequest(String jsonRequestString, String restServiceURL) {
        URL targetUrl = null;
        String jsonResponseStr = null;
        try {
            targetUrl = new URL(restServiceURL);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);

            httpConnection.setRequestMethod("POST");
            String ContentType = "application/json;charset=UTF-8";
            httpConnection.setRequestProperty("Content-Type", ContentType);

            OutputStream outputStream = httpConnection.getOutputStream();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "utf-8"));
            out.println(jsonRequestString);
            out.close();

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }
            // Get response data.
            InputStreamReader bf = new InputStreamReader(httpConnection.getInputStream(), "utf-8");
            String result = "";
            int b = 0;
            while ((b = bf.read()) != -1) {
                result += (char) b;
            }

            httpConnection.disconnect();

            jsonResponseStr = new String(result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponseStr;
    }

    private String encoderByMd5(String str) {
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

    public String send(String target, String content) {
        DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String user = "tztjxx";
        String siid = "TZSTJJXXC";
        String secretKey = "TZtjxx88";
        long currenttime = System.currentTimeMillis();
        String timeStamp = formater.format(currenttime);
        String transactionID = timeStamp;
        String streamingNo = siid + transactionID;
        String authenticator = encoderByMd5(timeStamp + transactionID + streamingNo + secretKey);
        String mobile = target;
        String req = "{\"siid\":\"" + siid + "\",\"user\":\"" + user + "\", \"streamingNo\":\"" + streamingNo + "\"" +
                ",\"timeStamp\":\"" + timeStamp + "\",\"transactionID\":\"" + transactionID + "\"" +
                ",\"authenticator\":\"" + authenticator + "\",\"mobile\":\"" + mobile + "\"" +
                ",\"content\":\"" + content + "\"}";

        System.out.println(req);
        String ret = instance.sendPostRequest(req, "http://115.239.134.217/smsservice/httpservices/capService");
        System.out.println(ret);
        return ret;
    }

    public static void main(String[] args) {
        LhzxHttpClientUtil.getInstance().send("18357162602", "asjbiuasdgagiguiig");
    }
}
