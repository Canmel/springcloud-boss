package com.camel.control.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.control.config.DeviceConfig;
import com.camel.control.model.DDevice;
import com.camel.control.service.DDeviceService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/device")
public class DDeviceController extends BaseCommonController {

    /**
     * 识别率阀值
     */
    public static final Integer THRESHOLDVALUE = 80;

    public static final String DEVICEREPORTBASEURL="http://192.168.1.7:8080/";

    public static final String DEVICEREPORTSUBURL = "control/device/add";



    @PostMapping("/add")
    public Result addDevice(String deviceNumber, String deviceIp) {

        return ResultUtil.success("success", new DeviceConfig());
    }

    @Autowired
    private DDeviceService service;

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

