package com.camel.sms.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.sms.service.AppPushNoticeService;
import com.camel.sms.service.WxNoticeService;
import com.gexin.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RestController;


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
 * <>
 * @author baily
 * @since 1.0
 * @date 2019/9/29
 **/
@RestController
public class WxNoticeController {

    public static Logger logger = LoggerFactory.getLogger(WxNoticeController.class);

    @Autowired
    private WxNoticeService service;

    @JmsListener(destination = "ActiveMQ.WxNotice.Topic")
    public Result notice(String msg) {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(msg);
        service.send(jsonObject.getString("openid"), jsonObject.getString("msg"));
        return ResultUtil.success("发送消息成功");
    }
}
