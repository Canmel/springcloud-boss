package com.camel.interviewer.controller;

import com.alibaba.fastjson.JSONObject;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.interviewer.annotation.AuthIgnore;
import com.camel.interviewer.config.WxConstants;
import com.camel.interviewer.exceptions.NotWxExplorerException;
import com.camel.interviewer.exceptions.WxServerConnectException;
import com.camel.interviewer.model.WxUser;
import com.camel.interviewer.service.WeixinStartService;
import com.camel.interviewer.service.WxSubscibeService;
import com.camel.interviewer.service.WxUserService;
import com.camel.interviewer.utils.HttpUtils;
import com.camel.interviewer.utils.MessageUtil;
import com.camel.interviewer.utils.WxTokenUtil;
import com.camel.interviewer.utils.XmlUtil;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/share")
public class WeixinShareController {

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

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private WeixinStartService weixinStartService;

    @AuthIgnore
    @GetMapping("/getQRCode")
    private Result qrCode(String code) {
        WxUser wxUser = weixinStartService.getUser(code);
        if(ObjectUtils.isEmpty(wxUser)) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "未找到您的相关信息，请先完善信息");
        }
        JSONObject tokenBody = null;
        try {
            String token = WxTokenUtil.getInstance().getTocken(wxConstants.getAppid(), wxConstants.getAppsecret());
            String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
            String  json = "{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+ wxUser.getOpenid() +"\"}}}";;
            Response responseBody = HttpUtils.httpPostResponse(url, new HashMap<String, String>(), json);
            tokenBody = JSONObject.parseObject(responseBody.body().string());
            if(StringUtils.isNotBlank(tokenBody.getString("errcode"))) {
                throw new NotWxExplorerException();
            }
            System.out.println(tokenBody.toJSONString());
        } catch (IOException e) {
            throw new WxServerConnectException();
        }
        return ResultUtil.success(tokenBody);
    }
}
