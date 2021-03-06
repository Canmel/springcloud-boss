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
 * <Uni推送>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
public class UniPushServer {
    /**
     * STEP1：获取应用基本信息
     */
    private static String appId = "CcP7u32rzr5SF1PYGgYNj8";
    private static String appKey = "Msw7TA0kJh9DFTiu5MOGZ2";
    private static String masterSecret = "TVsJONNDs87PKLJ66qMLF3";
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

    }

    public static NotificationTemplate getNotificationTemplate() {
        NotificationTemplate template = new NotificationTemplate();
        /** 设置APPID与APPKEY **/
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        /** 设置通知栏标题与内容 **/
        style.setTitle("请输入通知栏标题");
        style.setText("请输入通知栏内容");
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
        /** 设置通知渠道重要性 **/
        style.setChannelLevel(3);
        template.setStyle(style);
        /** 透传消息接受方式设置，1：立即启动APP，2：客户端收到消息后需要自行处理 **/
        template.setTransmissionType(1);
        template.setTransmissionContent("请输入您要透传的内容");
        return template;
    }
}
