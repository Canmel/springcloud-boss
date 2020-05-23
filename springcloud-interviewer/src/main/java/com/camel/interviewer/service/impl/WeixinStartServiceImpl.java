package com.camel.interviewer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.camel.interviewer.config.WxConstants;
import com.camel.interviewer.controller.WeixinStartController;
import com.camel.interviewer.exceptions.NotWxExplorerException;
import com.camel.interviewer.exceptions.WxServerConnectException;
import com.camel.interviewer.model.WxUser;
import com.camel.interviewer.service.WeixinStartService;
import com.camel.interviewer.service.WxUserService;
import com.camel.interviewer.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinStartServiceImpl implements WeixinStartService {

    @Autowired
    private WxConstants wxConstants;

    @Autowired
    private WxUserService wxUserService;

    @Override
    public WxUser getUser(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", wxConstants.getAppid());
        params.put("code", code);
        params.put("secret", wxConstants.getAppsecret());
        params.put("grant_type", WeixinStartController.AUTHORIZATION_CODE);
        JSONObject tokenBody = null;
        try {
            String responseBody = HttpUtils.httpGetMethod(WeixinStartController.USERID_URL, params);
            if (responseBody != null) {
                tokenBody = JSONObject.parseObject(responseBody);
                if(StringUtils.isNotBlank(tokenBody.getString("errcode"))) {
                    throw new NotWxExplorerException();
                }
                System.out.println(tokenBody.toJSONString());
            }
        } catch (IOException e) {
            throw new WxServerConnectException();
        }
        return wxUserService.selectByOpenid(tokenBody.getString("openid"));
    }
}
