package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.service.ZsUnionConfigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  联合配额控制器
 * </p>
 *
 * @author baily
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zsUnionConfigration")
public class ZsUnionConfigrationController extends BaseCommonController {

    @Autowired
    private ZsUnionConfigrationService service;



    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "联合配额";
    }
}

