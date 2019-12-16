package com.camel.survey.service;

import com.camel.survey.vo.ZsSendSms;

public interface ZsSmsService {
    boolean send(ZsSendSms sms);
}
