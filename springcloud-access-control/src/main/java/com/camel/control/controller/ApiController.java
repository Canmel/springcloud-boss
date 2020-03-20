package com.camel.control.controller;

import com.camel.control.config.DeviceConfig;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @PostMapping("/device/add")
    public Result addDevice(@RequestParam("deviceNumber") String deviceNumber, @RequestParam("deviceIp") String deviceIp) {

        return ResultUtil.success("success", new DeviceConfig());
    }
}
