package com.camel.interviewer.service;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.interviewer.entity.wx.WxZc;

import java.util.List;

public interface InterviewerService {
    Result save(SysUser sysUser);

    List<WxZc> AllZc();
}
