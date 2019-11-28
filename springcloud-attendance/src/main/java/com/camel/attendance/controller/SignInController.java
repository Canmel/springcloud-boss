package com.camel.attendance.controller;

import com.camel.attendance.model.SignRecords;
import com.camel.attendance.service.SignRecordsService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * <签到与签退>
 * @author baily
 * @since 1.0
 * @date 2019/11/21
 **/
@RestController
@RequestMapping("/sign")
public class SignInController {

    @Autowired
    private SignRecordsService signRecordsService;

    @PostMapping("/in")
    public Result signIn(@RequestBody SignRecords signRecords, OAuth2Authentication oAuth2Authentication) {
        // 新增签入签出记录
        signRecordsService.signIn(signRecords, oAuth2Authentication);
        return ResultUtil.success("");
    }

    @PostMapping("/out")
    public Result signOut(@RequestBody SignRecords signRecords, OAuth2Authentication oAuth2Authentication) {
        // 新增签入签出记录
        signRecordsService.signOut(signRecords, oAuth2Authentication);
        return ResultUtil.success("");
    }
}
