package com.camel.attendance.controller;

import com.camel.attendance.config.ApplicationConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
 * <首页>
 * @author baily
 * @since 1.0
 * @date 2019/11/19
 **/
@Controller
public class IndexController {

    @Autowired
    private ApplicationConfig applicationConfig;

    @GetMapping("")
    public String index(Principal principal, HttpServletResponse response) throws IOException {
        String getWayUrl = StringUtils.isEmpty(applicationConfig.getGetWayUrl()) ? "127.0.0.1" : applicationConfig.getGetWayUrl();
        String getWayPort = StringUtils.isEmpty(applicationConfig.getGetWayPort()) ? ":8080" : (":" + applicationConfig.getGetWayPort());
        if(ObjectUtils.isEmpty(principal)) {
            response.sendRedirect("http://" + getWayUrl + getWayPort + "/login?redirect_url=survey/index.html");
        }
        return "/index.html";
    }

}
