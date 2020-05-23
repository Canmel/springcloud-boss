package com.camel.interviewer.service;

import com.camel.interviewer.model.WxUser;

public interface WeixinStartService {
    WxUser getUser(String code);
}
