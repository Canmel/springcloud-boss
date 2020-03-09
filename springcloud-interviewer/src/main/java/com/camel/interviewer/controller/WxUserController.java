package com.camel.interviewer.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.interviewer.model.WxUser;
import com.camel.interviewer.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 * 微信用户信息 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/wxUser")
public class WxUserController extends BaseCommonController {

    @PostMapping
    private Result save(@RequestBody WxUser wxUser) {
        return super.save(wxUser);
    }

    @Autowired
    private WxUserService service;

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "用户信息";
    }
}

