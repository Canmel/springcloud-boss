package com.camel.sms.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.camel.core.utils.WxTokenUtil;
import com.camel.sms.config.WxConstants;
import com.camel.sms.service.WxNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxNoticeServiceImpl implements WxNoticeService {

    @Autowired
    private WxConstants wxConstants;

    @Override
    public void send(String openid, String msg) {
        String token = WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret(), null);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.put("touser", openid);
        jsonObject.put("msgtype", "text");
        JSONObject content = JSONUtil.createObj();
        content.put("content", msg);
        jsonObject.put("text", content);
        HttpRequest.post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token).header("Content-Type","application/json")
                .body(jsonObject.toString()).execute();
    }
}
