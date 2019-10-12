package com.camel.sms.service.impl;

import com.camel.sms.config.AppPushConfig;
import com.camel.sms.service.AppPushService;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * <移动端推送实现>
 * @author baily
 * @since 1.0
 * @date 2019/9/26
 **/
@Service
public class AppPushServiceImpl implements AppPushService {

    public static Logger logger = LoggerFactory.getLogger(AppPushServiceImpl.class);

    @Override
    public Boolean send() {
        IGtPush push = new IGtPush(AppPushConfig.URL, AppPushConfig.APPKEY, AppPushConfig.MASTERSECRET);
        NotificationTemplate template = getNotificationTemplate();
        SingleMessage message = new SingleMessage();

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

    public static NotificationTemplate getNotificationTemplate() {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(AppPushConfig.APPID);
        template.setAppkey(AppPushConfig.APPKEY);

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
