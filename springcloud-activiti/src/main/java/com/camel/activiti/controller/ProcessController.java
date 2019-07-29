package com.camel.activiti.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("process")
public class ProcessController {
    @GetMapping("pass")
    public Result pass(){
        return ResultUtil.success("审核成功");
    }

    @GetMapping("reject")
    public Result reject() {
        return ResultUtil.success("驳回成功");
    }

    @GetMapping("start")
    public Result start() {
        return ResultUtil.success("发起流程成功");
    }
}
