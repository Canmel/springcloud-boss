package com.camel.activiti.service.impl;

import com.camel.activiti.service.FeignService;
import com.camel.core.entity.Result;
import com.camel.feign.SpringCloudSystemFeignClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeignServiceImpl implements FeignService {
    @Resource
    private SpringCloudSystemFeignClient springCloudSystemFeignClient;

    @Override
    public Result allRole() {
        return springCloudSystemFeignClient.allRole();
    }

    @Override
    public Result usersByRole(Integer id) {
        return springCloudSystemFeignClient.usersByRole(id.toString());
    }
}
