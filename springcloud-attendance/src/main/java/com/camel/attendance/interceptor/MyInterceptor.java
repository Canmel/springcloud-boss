package com.camel.attendance.interceptor;

import com.camel.attendance.config.ApplicationConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * <拦截器>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@Component
public class MyInterceptor implements HandlerInterceptor {
    public static final String ACCESS_TOKEN = "access_token";

    public static final String AJAX_REQUEST_HEADER = "XMLHttpRequest";

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String getWayUrl = StringUtils.isEmpty(applicationConfig.getGetWayUrl()) ? "127.0.0.1" : applicationConfig.getGetWayUrl();
        String getWayPort = StringUtils.isEmpty(applicationConfig.getGetWayPort()) ? ":8080" : (":" + applicationConfig.getGetWayPort());
        if (isHtml(request)) {
            if (!isPjaxRequest(request)) {
                response.sendRedirect("http://" + getWayUrl + getWayPort + "/attendance/");
            }
            return true;
        }
        if (isNoLogin(request)) {
            if (isAjaxRequest(request)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else {
                response.sendRedirect("http://" + getWayUrl + getWayPort + "/login?redirect_url=attendance/index.html");

            }
            return false;
        }
        return true;
    }

    public boolean isAjaxRequest(HttpServletRequest request) {
        return AJAX_REQUEST_HEADER.equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    public boolean isNoLogin(HttpServletRequest request) {
        String[] paramStoken = request.getParameterValues(ACCESS_TOKEN);
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String token = details.getTokenValue();
        return ObjectUtils.isEmpty(paramStoken) && ObjectUtils.isEmpty(details) && ObjectUtils.isEmpty(details.getTokenValue());
    }

    public boolean isHtml(HttpServletRequest request) {
        return StringUtils.endsWith(request.getRequestURL().toString().toUpperCase(), ".HTML");
    }

    public boolean isPjaxRequest(HttpServletRequest request) {
        return !ObjectUtils.isEmpty(request.getHeader("x-pjax"));
    }
}
