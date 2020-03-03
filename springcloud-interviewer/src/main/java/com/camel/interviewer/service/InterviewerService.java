package com.camel.interviewer.service;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;

public interface InterviewerService {
    Result save(SysUser sysUser);
}
