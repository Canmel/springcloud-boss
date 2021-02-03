package com.camel.survey.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.camel.survey.enums.CtiResult;
import com.camel.survey.exceptions.SourceDataNotValidException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpUtils {

    public static final String CTI_RESP_STATUS = "statuscode";

    public static final String CTI_RESP_BODY = "body";

    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static JSON post(JSONObject jsonObject, String baseUrl, String path) {
        String resp = "";
        try {
            resp = HttpUtil.createPost("http://" + baseUrl + path).header("Content-Type", "application/json; charset=UTF-8").body(jsonObject.toString(), "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            String statuscode = respObject.getStr(CTI_RESP_STATUS);
            if (!statuscode.equals(CtiResult.SUCCESS.getCode())) {
                throw new SourceDataNotValidException(respObject.getStr(CTI_RESP_BODY));
            }
            if ("任务标识不存在".equals(respObject.getStr("body"))) {
                throw new SourceDataNotValidException(respObject.getStr("body"));
            }
            if (JSONUtil.isJsonObj(respObject.getStr(CTI_RESP_BODY))) {
                return respObject.getJSONObject(CTI_RESP_BODY);
            } else if (JSONUtil.isJsonArray(respObject.getStr(CTI_RESP_BODY))) {
                return respObject.getJSONArray(CTI_RESP_BODY);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new SourceDataNotValidException(e.getMessage());
        }
        return null;
    }


    public static JSON get(JSONObject jsonObject, String baseUrl, String path) {
        String resp = "";
        try {
            resp = HttpUtil.createPost("http://" + baseUrl + path).header("Content-Type", "application/json; charset=UTF-8").body(jsonObject.toString(), "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            String statuscode = respObject.getStr(CTI_RESP_STATUS);
            if (!statuscode.equals(CtiResult.SUCCESS.getCode())) {
                throw new SourceDataNotValidException(respObject.getStr(CTI_RESP_BODY));
            }
            if (JSONUtil.isJson(respObject.getStr(CTI_RESP_BODY))) {
                return respObject.getJSONArray(respObject.getStr(CTI_RESP_BODY));
            } else if (JSONUtil.isJsonArray(respObject.getStr(CTI_RESP_BODY))) {
                return respObject.getJSONArray(respObject.getStr(CTI_RESP_BODY));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new SourceDataNotValidException(e.getMessage());
        }
        return null;
    }
}
