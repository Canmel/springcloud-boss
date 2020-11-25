package com.camel.auth.controller;

import cn.hutool.http.HttpUtil;
import com.camel.auth.config.oauth.RedisTokenStore;
import com.camel.common.enumeration.ResultCode;
import com.camel.common.enums.ResultEnum;
import com.camel.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
 *
 * @author baily
 * @since 1.0
 * @date 2019/7/4
 **/
@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping
    public String user(String code) {
        String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=WX_APPID&secret=WX_SECRET&js_code=WX_JSCODE&grant_type=authorization_code";
        code2SessionUrl = code2SessionUrl.replaceAll("WX_APPID", "wx86580ae3b1e6c709");
        code2SessionUrl = code2SessionUrl.replaceAll("WX_JSCODE", code);
        code2SessionUrl = code2SessionUrl.replaceAll("WX_SECRET", "6564633d842fb76e8f958614f88add6d");

        String result = HttpUtil.get(code2SessionUrl);

        return result;
    }
}
