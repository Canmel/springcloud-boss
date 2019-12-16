package com.camel.sms.controller;

import com.gexin.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * <短信>
 * @author baily
 * @since 1.0
 * @date 2019/12/16
 **/
@RestController
public class SmsController {

    public static final String TARGET = "target";
    public static final String CONTENT = "content";

    public static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @JmsListener(destination = "ActiveMQ.Sms.Topic")
    public void log(String msg) {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(msg);
        logger.info("手机号: " + jsonObject.get(TARGET));
        logger.info("发送内容: " + jsonObject.get(CONTENT));
        logger.info(msg);
    }
}
