package com.camel.interviewer.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private void event(@RequestBody Map<String, String> map, HttpServletResponse response) throws IOException {
        String fromUserName = map.get("FromUserName");//消息来源用户标识
        String toUserName = map.get("ToUserName");//消息目的用户标识
        String msgType = map.get("MsgType");//消息类型
        String content = map.get("Content");//消息内容
        String eventKey = map.get("EventKey");//自定义参数
        String eventType = map.get("Event");
        //如果为事件类型
        if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
            //处理订阅事件
            if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
                logger.info("订阅事件推送");
                MessageUtil.subscribeForImageText(toUserName, fromUserName);
                wxSubscibeService.save(fromUserName, eventKey);
                //处理取消订阅事件
            } else if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
                MessageUtil.unsubscribe(toUserName, fromUserName);
                logger.info("取消订阅事件推送");
                wxSubscibeService.unsave(fromUserName);
            }
        }
    }

    @AuthIgnore
    @GetMapping("/getToken")
    private Result qrCode(String code) {
        WxUser wxUser = weixinStartService.getUser(code);
        if (ObjectUtils.isEmpty(wxUser)) {
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
    private Result signature(String currentUrl) {
        String token = WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret(), redisTemplate);
        String ticket = JsapiTicketUtil.getInstance().JsapiTicket(token, redisTemplate);
        Map<String, String> ret = sign(ticket, currentUrl);
        for (Map.Entry entry : ret.entrySet()) {
            logger.info(entry.getKey() + ", " + entry.getValue());
        }
        String randomStr = WxCommonsUtils.getInstance().getRandomStr();
        String timestamp = WxCommonsUtils.getInstance().timeStamp();
        Map<String, Object> map = new HashMap();
        map.put("noncestr", randomStr);
        map.put("jsapi_ticket", ticket);
        map.put("timestamp", timestamp);
        map.put("url", currentUrl);
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

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    @AuthIgnore
    @GetMapping("/sendMsg")
    private Result sendMsg(String msg, String openid) {
        String token = WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret(), redisTemplate);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.put("touser", openid);
        jsonObject.put("msgtype", "text");
        JSONObject content = JSONUtil.createObj();
        content.put("content", msg);
        jsonObject.put("text", content);
        HttpRequest.post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token).header("Content-Type","application/json")
                .body(jsonObject.toString()).execute();
        return null;

    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
