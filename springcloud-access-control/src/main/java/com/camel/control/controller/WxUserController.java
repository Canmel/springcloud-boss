package com.camel.control.controller;


import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信用户信息 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/wxUser")
public class WxUserController extends BaseCommonController {
    @Override
    public IService getiService() {
        return null;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

