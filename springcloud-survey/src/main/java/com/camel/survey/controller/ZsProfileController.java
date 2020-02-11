package com.camel.survey.controller;

import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.service.ZsProfileService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.krb5.internal.AuthContext;

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
 * <个人信息>
 * @author baily
 * @since 1.0
 * @date 2019/12/13
 **/
@RestController
@RequestMapping("/profile")
public class ZsProfileController {

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private ZsProfileService service;

    @GetMapping
    public Result index(OAuth2Authentication oAuth2Authentication) {
        Member member = applicationToolsUtils.currentUser(oAuth2Authentication);
        return ResultUtil.success(applicationToolsUtils.getUser(member.getId()));
    }

    @GetMapping("/total")
    public Result total(){
        Member member = ApplicationToolsUtils.getInstance().currentUser();
        return ResultUtil.success(service.total(member.getId()));
    }
}
