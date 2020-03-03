package com.camel.interviewer.service.impl;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.interviewer.feign.SpringCloudSystemFeignClient;
import com.camel.interviewer.service.InterviewerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InterviewerServiceImpl implements InterviewerService {

    @Resource
    private SpringCloudSystemFeignClient springCloudSystemFeignClient;

    @Override
    public Result save(SysUser sysUser) {
        return springCloudSystemFeignClient.interviewerSave(sysUser);
    }
}
