package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/web_survey")
public class ZsIgnoreController {
    @GetMapping
    @AuthIgnore
    public Result index() {
        return ResultUtil.success("我是");
    }
}
