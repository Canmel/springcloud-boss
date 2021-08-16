package com.camel.auth.controller;

import com.camel.auth.config.oauth.PicKaptchaUtil;
import com.camel.auth.config.oauth.RedisTokenStore;
import com.camel.common.utils.Result;
import com.camel.common.enumeration.ResultCode;
import com.camel.common.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private RedisTokenStore redisTokenStore;

    @Autowired
    private PicKaptchaUtil picKaptchaUtil;

    @GetMapping("/me")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/info")
    public Result info(Principal principal) {
        return new Result(ResultEnum.SUCCESS.getCode(), "操作成功", principal, true);
    }

    @GetMapping("/code")
    public Result imageCode(HttpServletRequest request, HttpServletResponse response) {
        return new Result(200, "操作成功", picKaptchaUtil.kaptcha(request, response), true);
    }

    @DeleteMapping(value = "/exit")
    public com.camel.common.entity.Result revokeToken(String access_token) {
        com.camel.common.entity.Result result = new com.camel.common.entity.Result();
        if (consumerTokenServices.revokeToken(access_token)) {
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setMessage("注销成功");
        } else {
            result.setCode(ResultCode.FAILED.getCode());
            result.setMessage("注销失败");
        }
        return result;
    }
}
