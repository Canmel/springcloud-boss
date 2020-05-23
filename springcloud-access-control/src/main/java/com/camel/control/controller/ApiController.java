package com.camel.control.controller;

import com.camel.control.config.DeviceConfig;
import com.camel.control.feign.SpringCloudSurveyFeignClient;
import com.camel.control.model.ResultAccess;
import com.camel.control.service.DDeviceService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private DDeviceService deviceService;

    @Autowired
    private SpringCloudSurveyFeignClient springCloudSurveyFeignClient;

    @PostMapping("/device/add")
    public Result addDevice(@RequestParam("deviceNumber") String deviceNumber, @RequestParam("deviceIp") String deviceIp) {

        return ResultUtil.success("success", new DeviceConfig());
    }

    /**
     * 该方法需要ZS_work_shift的支持
     * @param deviceNumber
     * @param idNumber
     * @return
     */
    @PostMapping("/blacklist/findByIdNumber")
    public ResultAccess findByIdNumber(@RequestParam("deviceNumber") String deviceNumber, @RequestParam("idNumber") String idNumber){
        Result result = springCloudSurveyFeignClient.selectWorkR(idNumber);
        return deviceService.findDDeviceByDeviceNumber(deviceNumber,result.getMsg());
    }
}
