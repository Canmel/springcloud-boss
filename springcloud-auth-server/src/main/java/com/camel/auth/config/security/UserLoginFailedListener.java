package com.camel.auth.config.security;

import com.camel.auth.dao.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserLoginFailedListener implements ApplicationListener<UserLoginFailedEvent> {
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public void onApplicationEvent(UserLoginFailedEvent event) {
        System.out.println("----用户验证信息---faile----------------------");
        sysUserDao.addFaile(event.getMsg());
    }
}
