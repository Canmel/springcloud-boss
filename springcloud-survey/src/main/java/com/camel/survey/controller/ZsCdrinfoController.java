package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.service.ZsCdrinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
@Controller
@RequestMapping("/zsCdrinfo")
public class ZsCdrinfoController extends BaseCommonController {

    @Autowired
    private ZsCdrinfoService service;

    @PostMapping
    public Result save(@RequestBody ZsCdrinfo zsCdrinfo) {
        return super.save(zsCdrinfo);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "话单推送";
    }
}

