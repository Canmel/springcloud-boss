package com.camel.gateway.controller;

import cn.hutool.http.HttpUtil;
import com.netflix.zuul.util.HTTPRequestUtils;
import com.sun.deploy.net.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * ___====-_  _-====___
 * _--^^^#####//      \\#####^^^--_
 * _-^##########// (    ) \\##########^-_
 * -############//  |\^^/|  \\############-
 * _/############//   (@::@)   \\############\_
 * /#############((     \\//     ))#############\
 * -###############\\    (oo)    //###############-
 * -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 * _#/|##########/\######(   /\   )######/\##########|\#_
 * |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 * `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 * `   `  `      `   / | |  | | \   '      '  '   '
 * (  | |  | |  )
 * __\ | |  | | /__
 * (vvv(VVV)(VVV)vvv)
 * <控制器>
 *
 * @author baily
 * @date 2019/7/7
 * @since 1.0
 **/
@RestController
@RequestMapping("/session")
public class SessionController {
    @GetMapping("/me")
    public Principal me(HttpSession session, Principal principal) {
        return principal;
    }

    @GetMapping("/wx")
    public String wxLogin(String code) {
        String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=WX_APPID&secret=WX_SECRET&js_code=WX_JSCODE&grant_type=authorization_code";
        code2SessionUrl = code2SessionUrl.replaceAll("WX_APPID", "wx86580ae3b1e6c709");
        code2SessionUrl = code2SessionUrl.replaceAll("WX_JSCODE", code);
        code2SessionUrl = code2SessionUrl.replaceAll("WX_SECRET", "6564633d842fb76e8f958614f88add6d");

        String result = HttpUtil.get(code2SessionUrl);

        return result;
    }
}
