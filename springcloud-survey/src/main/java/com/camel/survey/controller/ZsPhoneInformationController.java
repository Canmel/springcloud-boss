package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsPhoneInformation;
import com.camel.survey.service.ZsPhoneInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-06-10
 */
@Controller
@RequestMapping("/zsPhoneInformation")
public class ZsPhoneInformationController extends BaseCommonController {

    @Autowired
    private ZsPhoneInformationService service;

    @GetMapping
    public Result index(ZsPhoneInformation zsPhoneInformation) {
        return ResultUtil.success(service.pageQuery(zsPhoneInformation));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "号码信息";
    }
}

