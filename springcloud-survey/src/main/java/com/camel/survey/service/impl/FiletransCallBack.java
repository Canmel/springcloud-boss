package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.camel.survey.model.ZsSentenceResult;
import com.camel.survey.service.MyFileTransterBackUpdate;
import com.camel.survey.service.ZsSentenceResultService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FiletransCallBack implements MyFileTransterBackUpdate {

    @Autowired
    private ZsSentenceResultService sentenceResultService;

    // 以4开头的状态码是客户端错误
    private static final Pattern PATTERN_CLIENT_ERR = Pattern.compile("4105[0-9]*");
    // 以5开头的状态码是服务端错误
    private static final Pattern PATTERN_SERVER_ERR = Pattern.compile("5105[0-9]*");

    // 必须是post的方式
    @Override
    public void update(HttpServletRequest request) {
        byte [] buffer = new byte[request.getContentLength()];
        ServletInputStream in = null;
        try {
            in = request.getInputStream();
            in.read(buffer, 0 ,request.getContentLength());
            in.close();
            // 获取json格式的文件转写结果
            String result = new String(buffer);
            JSONObject jsonResult = JSONObject.parseObject(result);
            // 解析并输出相关结果内容
            System.out.println("获取文件中转写回调结果:" + result);
            System.out.println("TaskId: " + jsonResult.getString("TaskId"));
            System.out.println("StatusCode: " + jsonResult.getString("StatusCode"));
            System.out.println("StatusText: " + jsonResult.getString("StatusText"));
            Matcher matcherClient = PATTERN_CLIENT_ERR.matcher(jsonResult.getString("StatusCode"));
            Matcher matcherServer = PATTERN_SERVER_ERR.matcher(jsonResult.getString("StatusCode"));
            // 以2开头状态码为正常状态码，回调方式方式正常状态只返回"21050000"
            if("21050000".equals(jsonResult.getString("StatusCode"))) {
                System.out.println("RequestTime: " + jsonResult.getString("RequestTime"));
                System.out.println("SolveTime: " + jsonResult.getString("SolveTime"));
                System.out.println("BizDuration: " + jsonResult.getString("BizDuration"));
                System.out.println("Result.Sentences.size: " +
                        jsonResult.getJSONObject("Result").getJSONArray("Sentences").size());
                for (int i = 0; i < jsonResult.getJSONObject("Result").getJSONArray("Sentences").size(); i++) {
                    JSONObject jsonObject = jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i);
                    Integer channelId = jsonObject.getInteger("ChannelId");
                    ZsSentenceResult zsSentenceResult = new ZsSentenceResult(
                            jsonResult.getString("TaskId"),
                            jsonObject.getInteger("ChannelId"),
                            jsonObject.getInteger("BeginTime"),
                            jsonObject.getInteger("EndTime"),
                            jsonObject.getString("Text"),
                            jsonObject.getDouble("EmotionValue"),
                            jsonObject.getInteger("SilenceDuration"),
                            jsonObject.getInteger("speechRate")
                            );
                    sentenceResultService.insert(zsSentenceResult);
                }
            }
            else if(matcherClient.matches()) {
                System.out.println("状态码以4开头表示客户端错误......");
            }
            else if(matcherServer.matches()) {
                System.out.println("状态码以5开头表示服务端错误......");
            }
            else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String result) {
        System.out.println("更新完成" + result);
    }
}
