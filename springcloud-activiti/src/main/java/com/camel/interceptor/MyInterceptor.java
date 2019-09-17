package com.camel.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    public static final String ACCESS_TOKEN = "access_token";

    public static final String AJAX_REQUEST_HEADER = "XMLHttpRequest";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(isHtml(request)) {
            if(!isPjaxRequest(request)) {
                response.sendRedirect("http://127.0.0.1:8080/acti/index.html");
            }
            return true;
        }
        if (isNoLogin(request)) {
            if(isAjaxRequest(request)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }else {
                response.sendRedirect("http://127.0.0.1:8080/login?redirect_url=acti/index.html");

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
        OAuth2AuthenticationDetails details  = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String token = details.getTokenValue();
        return ObjectUtils.isEmpty(paramStoken) && ObjectUtils.isEmpty(details) && ObjectUtils.isEmpty(details.getTokenValue());
    }

    public boolean isHtml(HttpServletRequest request){
        return StringUtils.endsWith(request.getRequestURL().toString().toUpperCase(), ".HTML");
    }

    public boolean isPjaxRequest(HttpServletRequest request) {
        return !ObjectUtils.isEmpty(request.getHeader("x-pjax"));
    }
}
