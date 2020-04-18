package com.camel.survey.controller;

import com.camel.survey.config.ApplicationConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

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
 * <页面跳转>
 * @author baily
 * @since 1.0
 * @date 2020/1/20
 **/
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private ApplicationConfig config;

    @GetMapping("/input")
    public RedirectView input(HttpServletRequest request) {
        String getWayUrl = StringUtils.isEmpty(config.getGetWayUrl()) ? "127.0.0.1" : config.getGetWayUrl();
        String getWayPort = StringUtils.isEmpty(config.getGetWayPort()) ? ":8080" : (":" + config.getGetWayPort());
        RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        redirectTarget.setUrl("http://" + getWayUrl + getWayPort + "/survey/input_s.html?surveyId=3");
        return redirectTarget;
    }
}
