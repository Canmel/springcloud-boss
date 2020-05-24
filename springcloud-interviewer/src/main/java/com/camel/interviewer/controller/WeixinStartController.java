package com.camel.interviewer.controller;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.interviewer.annotation.AuthIgnore;
import com.camel.interviewer.config.WxConstants;
import com.camel.interviewer.model.WxJsConfig;
import com.camel.interviewer.model.WxUser;
import com.camel.interviewer.service.WeixinStartService;
import com.camel.interviewer.service.WxSubscibeService;
import com.camel.interviewer.utils.*;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    public static Logger logger = LoggerFactory.getLogger(WeixinStartService.class);

    @Autowired
    private WeixinStartService service;

    @Autowired
    private WxConstants wxConstants;

    @Autowired
    private WxSubscibeService wxSubscibeService;

    @Autowired
    private WeixinStartService weixinStartService;

    @Autowired
    private RedisTemplate redisTemplate;

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
            String eventKey = map.get("EventKey");//自定义参数
            String eventType = map.get("Event");
            //如果为事件类型
            if (MessageUtil.MSGTYPE_EVENT.equals(msgType)) {
                //处理订阅事件
                if (MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)) {
                    logger.info("订阅事件推送");
                    message = MessageUtil.subscribeForText(toUserName, fromUserName);
                    wxSubscibeService.save(fromUserName, eventKey);
                    //处理取消订阅事件
                } else if (MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)) {
                    message = MessageUtil.unsubscribe(toUserName, fromUserName);
                    logger.info("取消订阅事件推送");
                    wxSubscibeService.unsave(fromUserName);
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
    @GetMapping("/getToken")
    private Result qrCode(String code) {
        WxUser wxUser = weixinStartService.getUser(code);
        if(ObjectUtils.isEmpty(wxUser)) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "未找到您的相关信息，请先完善信息");
        }
        String token = WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret(), redisTemplate);
        return ResultUtil.success(token);
    }

    @AuthIgnore
    @GetMapping("/token")
    private Result token() {
        return ResultUtil.success(WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret(), redisTemplate));
    }

    @AuthIgnore
    @GetMapping("/ticket")
    private Result ticket() {
        String token = WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret(), redisTemplate);
        return ResultUtil.success(JsapiTicketUtil.getInstance().JsapiTicket(token, redisTemplate));
    }

    @AuthIgnore
    @GetMapping("/signature")
    private Result signature() {
        String token = WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret(), redisTemplate);
        String ticket = JsapiTicketUtil.getInstance().JsapiTicket(token, redisTemplate);
        String randomStr = WxCommonsUtils.getInstance().getRandomStr();
        String timestamp = WxCommonsUtils.getInstance().timeStamp();
        Map<String, Object> map = new HashMap();
        map.put("noncestr", randomStr);
        map.put("jsapi_ticket", ticket);
        map.put("timestamp", timestamp);
        map.put("url", "https://diaocha.svdata.cn/viewer/view/share");
        String signatureStr = MapUrlParamsUtils.getUrlParamsByMap(map);
        String signature = SHA1.encode(signatureStr);
        logger.info(signatureStr);
        logger.info(signature);
        WxJsConfig wxJsConfig = new WxJsConfig(wxConstants.getAppid(), timestamp, randomStr, signature);
        return ResultUtil.success(wxJsConfig);
    }

    @AuthIgnore
    @GetMapping("getUserInfo")
    private Result getUserInfo(String code) {
        return ResultUtil.success(service.getUser(code));
    }
}
