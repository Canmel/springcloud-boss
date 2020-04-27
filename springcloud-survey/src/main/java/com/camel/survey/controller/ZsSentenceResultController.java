package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.service.ZsSentenceResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
@Controller
@RequestMapping("/zsSentenceResult")
public class ZsSentenceResultController extends BaseCommonController {

    @Autowired
    private ZsSentenceResultService service;

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "对话样本";
    }
}

