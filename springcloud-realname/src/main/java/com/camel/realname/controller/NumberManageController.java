package com.camel.realname.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/numberManage")
public class NumberManageController {

    @GetMapping
    public Result index(){

        return ResultUtil.success("success");
    }
}
