package com.camel.control.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.control.config.DeviceConfig;
import com.camel.control.model.DDevice;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "/valid", produces = "application/json;charset=UTF-8")
    public Result addDevice(@RequestBody DDevice device) {

        return ResultUtil.success("success");
    }

    @Data
    public class ValidResult {
        private String personnelType;
    }

    @Override
    public IService getiService() {
        return null;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

