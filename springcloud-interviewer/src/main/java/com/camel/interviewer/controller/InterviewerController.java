package com.camel.interviewer.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.interviewer.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interviewer")
public class InterviewerController extends BaseCommonController {

    @Autowired
    private InterviewerService interviewerService;

    @GetMapping
    private Result index() {
        return ResultUtil.success("成功");
    }

    @PostMapping
    public Result save(SysUser sysUser) {
        return interviewerService.save(sysUser);
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
