package com.camel.control.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.control.config.DeviceConfig;
import com.camel.control.model.DDevice;
import com.camel.control.service.DDeviceService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

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

    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public Result addDevice(@RequestBody DDevice device) {
        DeviceConfig config = new DeviceConfig();
        DDevice dDevice = DeviceConfig.newDevice(device, config);
        service.save(device);
        return ResultUtil.success("success", dDevice);
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

