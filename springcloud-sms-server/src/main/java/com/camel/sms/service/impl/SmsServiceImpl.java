package com.camel.sms.service.impl;

import com.camel.sms.enums.SmsStatus;
import com.camel.sms.service.SmsService;
import com.camel.sms.utils.LhzxHttpClientUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
 * <短信服务实现>
 * @author baily
 * @since 1.0
 * @date 2019/9/19
 **/
@Service
public class SmsServiceImpl implements SmsService {
    public static final String httpUrl = "http://api.smsbao.com/sms";

    @Override
    public boolean send(String target, String content) {
        String testUsername = "ucount"; //在短信宝注册的用户名
        String testPassword = "lxg730124"; //在短信宝注册的密码
        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(testUsername).append("&");
        httpArg.append("p=").append(LhzxHttpClientUtil.md5(testPassword)).append("&");
        httpArg.append("m=").append(target).append("&");
        httpArg.append("c=").append(LhzxHttpClientUtil.encodeUrlString(content, "UTF-8"));

        String result = LhzxHttpClientUtil.request(httpUrl, httpArg.toString());
        SmsStatus smsStatus = LhzxHttpClientUtil.getSendStatus(result);
        System.out.println(smsStatus.getText());
        return true;
    }


}
