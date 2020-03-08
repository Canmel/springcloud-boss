package com.camel.interviewer.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.interviewer.annotation.AuthIgnore;
import com.camel.interviewer.config.WxConstants;
import com.camel.interviewer.model.WxSubscibe;
import com.camel.interviewer.service.WxSubscibeService;
import com.camel.interviewer.utils.HttpUtils;
import com.camel.interviewer.utils.MessageUtil;
import com.camel.interviewer.utils.XmlUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class WeixinStartController {

    public static final String USERID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String SIGNATURE = "signature";
    public static final String TIMESTAMP = "timestamp";
    public static final String NONCE = "nonce";
    public static final String ECHOSTR = "echostr";
    public static final String AUTHORIZATION_CODE = "authorization_code";


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private WxConstants wxConstants;

    @Autowired
    private WxSubscibeService wxSubscibeService;

    @AuthIgnore
    @GetMapping
    private String start(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter(SIGNATURE);
        String timestamp = request.getParameter(TIMESTAMP);
        String nonce = request.getParameter(NONCE);
        String echostr = request.getParameter(ECHOSTR);
        return echostr;
    }

    @AuthIgnore
    @PostMapping
    private void event(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String message = "success";
        try {
            Map<String, String> map = XmlUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");//消息来源用户标识
            String toUserName = map.get("ToUserName");//消息目的用户标识
            String msgType = map.get("MsgType");//消息类型
            String content = map.get("Content");//消息内容

            String eventType = map.get("Event");
            //如果为事件类型
            if (MessageUtil.MSGTYPE_EVENT.equals(msgType)) {
                //处理订阅事件
                if (MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)) {
                    message = MessageUtil.subscribeForText(toUserName, fromUserName);
                    wxSubscibeService.save(toUserName);
                    //处理取消订阅事件
                } else if (MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)) {
                    message = MessageUtil.unsubscribe(toUserName, fromUserName);
                    wxSubscibeService.unsave(toUserName);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.println(message);
            if (out != null) {
                out.close();
            }
        }
    }

    @AuthIgnore
    @GetMapping("getUserInfo")
    private Object getUserInfo(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", wxConstants.getAppid());
        params.put("code", code);
        params.put("secret", wxConstants.getAppsecret());
        params.put("grant_type", AUTHORIZATION_CODE);
        Object result = null;
        try {
            String responseBody = HttpUtils.httpGetMethod(USERID_URL, params);
            if (responseBody != null) {
                JSONObject tokenBody = JSONObject.parseObject(responseBody);
                System.out.println(tokenBody.toJSONString());
                result = tokenBody;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
