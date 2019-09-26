package com.camel.sms.example;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.ArrayList;
import java.util.List;

public class UniPushServer {
    // STEP1：获取应用基本信息
    private static String appId = "e7zAJkqWjJ8QzoB6DIj2sA";
    private static String appKey = "rBCWAkA4eM8dH60dSd3UF6";
    private static String masterSecret = "C5WqTmwr7A7Eo13Audfod2";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) {
        send();
    }

    public static void send() {
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        NotificationTemplate template = getNotificationTemplate();
        SingleMessage message = new SingleMessage();

        AppMessage appMessage = new AppMessage();
        appMessage.setOffline(true);
        appMessage.setOfflineExpireTime(24 * 3600 * 1000);
        appMessage.setData(template);
        appMessage.setPushNetWorkType(0);
        List<String> appList = new ArrayList<>();
        appList.add(appId);
        appMessage.setAppIdList(appList);




//        message.setOffline(true);
//        // 离线有效时间，单位为毫秒
//        message.setOfflineExpireTime(24 * 3600 * 1000);
//        message.setData(template);
//        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
//        message.setPushNetWorkType(0);
//
//
//        Target target = new Target();
//        target.setAppId(appId);


        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToApp(appMessage);
        } catch (RequestException e) {
            e.printStackTrace();
//            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }

    }

    public static NotificationTemplate getNotificationTemplate() {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle("请输入通知栏标题");
        style.setText("请输入通知栏内容");
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        style.setChannel("通知渠道id");
        style.setChannelName("通知渠道名称");
        style.setChannelLevel(3); //设置通知渠道重要性
        template.setStyle(style);

        template.setTransmissionType(1);  // 透传消息接受方式设置，1：立即启动APP，2：客户端收到消息后需要自行处理
        template.setTransmissionContent("请输入您要透传的内容");
        return template;
    }
}
