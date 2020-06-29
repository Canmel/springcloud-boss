package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.service.ZsProfileService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 获取当前用户
     * @param oAuth2Authentication
     * @return
     */
    @GetMapping
    public Result index(OAuth2Authentication oAuth2Authentication) {
        SysUser member = applicationToolsUtils.currentUser();
        return ResultUtil.success(applicationToolsUtils.getUser(member.getUid()));
    }

    /**
     * 列出所有问卷
     * @return
     */
    @GetMapping("/total")
    public Result total(){
        SysUser member = ApplicationToolsUtils.getInstance().currentUser();
        return ResultUtil.success(service.total(member.getUid()));
    }
}
