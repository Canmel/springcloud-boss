package com.camel.survey.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.service.PstnnumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pstnnumber")
public class PstnnumberController {
    @Autowired
    private PstnnumberService service;

    @GetMapping("/all")
    private Result all() {
        return ResultUtil.success(service.all());
    }
}
