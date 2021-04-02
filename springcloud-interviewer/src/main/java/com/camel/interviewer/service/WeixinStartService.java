package com.camel.interviewer.service;

import com.camel.interviewer.model.WxUser;

import java.util.List;

public interface WeixinStartService {
    WxUser getUser(String code);

    List<String> getLoginUserRole(WxUser wxUser);
}
