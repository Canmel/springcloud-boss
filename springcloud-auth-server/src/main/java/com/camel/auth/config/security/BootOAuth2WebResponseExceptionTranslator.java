package com.camel.auth.config.security;

import com.camel.common.entity.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component("bootWebResponseExceptionTranslator")
public class BootOAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) {
        ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        Result result = new Result();
        if (e instanceof UnsupportedGrantTypeException) {
            result.setMessage("不支持该认证类型");
            result.setCode(400);
            return status.body(result);
        }
        if (e instanceof InvalidTokenException
                && StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
            result.setMessage("刷新令牌已过期，请重新登录");
            result.setCode(400);
            return status.body(result);
        }
        if (e instanceof InvalidScopeException) {
            result.setMessage("不是有效的scope值");
            result.setCode(400);
            return status.body(result);
        }
        if (e instanceof InvalidGrantException) {
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
                result.setMessage("refresh token无效");
                result.setCode(400);
                return status.body(result);
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
                result.setMessage("用户已被锁定，请联系管理员");
                result.setCode(400);
                return status.body(result);
            }
            result.setMessage("用户名或密码错误");
            result.setCode(400);
            return status.body(result);
        }
        result.setMessage("认证失败");
        result.setCode(400);
        return status.body(result);
    }
}
