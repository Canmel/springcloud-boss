package com.camel.interviewer.controller;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.interviewer.annotation.AuthIgnore;
import com.camel.interviewer.model.ZsSendSms;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/valid")
public class WxValidController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @AuthIgnore
    @GetMapping("/send")
    public Result smsCode(String phone) {
        if (isMobileNO(phone)) {
            String sms = "【赛炜调查】您的验证码为code，在time分钟内有效。";
            String code = String.format("%04d", new Random().nextInt(9999));
            System.out.println("验证码为:  " + code);
            sms = sms.replaceAll("code", code);
            sms = sms.replaceAll("time", "15");
            // 查看缓存中有没有改手机号的验证码
            if(ObjectUtils.isEmpty(redisTemplate.opsForValue().get(phone))) {
                // 暂存验证码15分钟
                ZsSendSms zsSendSms = new ZsSendSms(phone, sms);
                redisTemplate.opsForValue().set(phone, code, 15, TimeUnit.MINUTES);
                this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Sms.Valid.Topic"), zsSendSms.toString());
            }
            return ResultUtil.success("验证码已发送到手机");
        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "手机号不正确");
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1]\\d{10}";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }

    public static String randomCode() {
        return "" + Math.random();
    }
}
