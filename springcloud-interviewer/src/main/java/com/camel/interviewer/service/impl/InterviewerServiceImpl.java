package com.camel.interviewer.service.impl;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.interviewer.entity.wx.WxZc;
import com.camel.interviewer.feign.SpringCloudSystemFeignClient;
import com.camel.interviewer.mapper.WxUserMapper;
import com.camel.interviewer.service.InterviewerService;
import com.camel.interviewer.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InterviewerServiceImpl implements InterviewerService {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Resource
    private SpringCloudSystemFeignClient springCloudSystemFeignClient;

    @Override
    public Result save(SysUser sysUser) {
        return springCloudSystemFeignClient.interviewerSave(sysUser);
    }

    @Override
    public List<WxZc> AllZc() {
        return wxUserMapper.allWxZc();
    }
}
