package com.camel.auth.config.security;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

public class UserLoginFailedEvent extends ApplicationEvent {
    public UserLoginFailedEvent(Authentication authentication, String msg) {
        super(authentication);
        this.msg = msg;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
