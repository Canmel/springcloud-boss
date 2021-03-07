package com.camel.auth.config.security;

import com.camel.auth.model.MyUserDetails;
import com.camel.auth.service.MyUserDetailServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.openssl.PasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isBlank(username)){
            throw new UsernameNotFoundException("username用户名不可以为空");
        }
        if(StringUtils.isBlank(password)){
            throw new BadCredentialsException("密码不可以为空");
        }
        //获取用户信息
        UserDetails user = userDetailService.loadUserByUsername(username);
        //比较前端传入的密码明文和数据库中加密的密码是否相等
        if (!password.equals(user.getPassword())) {
            //发布密码不正确事件
            publisher.publishEvent(new UserLoginFailedEvent(authentication, username));
            throw new BadCredentialsException("密码不正确");
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, password, authorities);;
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
