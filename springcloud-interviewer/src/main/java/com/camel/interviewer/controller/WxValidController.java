package com.camel.interviewer.controller;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/valid")
public class WxValidController {

    @GetMapping("/code/send/{id}")
    public Result smsCode(@PathVariable String phone) {
        if(isMobileNO(phone)) {
            String.format("%04d",new Random().nextInt(9999));
            return ResultUtil.success("验证码已发送到手机");
        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(),"手机号不正确");
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }

    public static String randomCode() {
        return ""+Math.random();
    }
}
