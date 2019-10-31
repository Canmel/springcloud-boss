package com.camel.sms.service.impl;

import com.camel.sms.config.AppPushConfig;
import com.camel.sms.service.AppPushNoticeService;
import com.camel.sms.service.AppPushService;
import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
 * <APP推送消息>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@Service
public class AppPushNoticeServiceImpl implements AppPushNoticeService {
    @Override
    public Boolean send(String msg) {
        System.out.println(msg);
        IGtPush push = new IGtPush(AppPushConfig.URL, AppPushConfig.APPKEY, AppPushConfig.MASTERSECRET);
        NotificationTemplate template = getNotificationTemplate(msg);
        AppMessage appMessage = new AppMessage();
        appMessage.setOffline(true);
        appMessage.setOfflineExpireTime(24 * 3600 * 1000);
        appMessage.setData(template);
        appMessage.setPushNetWorkType(0);
        List<String> appList = new ArrayList<>();
        appList.add(AppPushConfig.APPID);
        appMessage.setAppIdList(appList);


        IPushResult ret = null;
        try {
            ret = push.pushMessageToApp(appMessage);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
        return true;
    }

    public static NotificationTemplate getNotificationTemplate(String msg) {
        NotificationTemplate template = new NotificationTemplate();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(msg);
        /** 设置APPID与APPKEY **/
        template.setAppId(AppPushConfig.APPID);
        template.setAppkey(AppPushConfig.APPKEY);

        Style0 style = new Style0();
        /** 设置通知栏标题与内容 **/
        style.setTitle((String) jsonObject.get(AppPushConfig.PUSH_TITLE_KEY));
        style.setText((String) jsonObject.get(AppPushConfig.PUSH_CONTEXT_KEY));
        /** 配置通知栏图标 **/
        style.setLogo("icon.png");
        /** 配置通知栏网络图标 **/
        style.setLogoUrl("");
        /** 设置通知是否响铃，震动，或者可清除 **/
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        style.setChannel("通知渠道id");
        style.setChannelName("通知渠道名称");
        /** 设置通知渠道重要性 */
        style.setChannelLevel(3);
        template.setStyle(style);
        /** 透传消息接受方式设置，1：立即启动APP，2：客户端收到消息后需要自行处理 **/
        template.setTransmissionType(1);
        template.setTransmissionContent("请输入您要透传的内容");
        return template;
    }
}
