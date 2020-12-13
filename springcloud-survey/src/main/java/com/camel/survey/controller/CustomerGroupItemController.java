package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.service.CustomerGroupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 * 打捆明细 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-13
 */
@Controller
@RequestMapping("/customerGroupItem")
public class CustomerGroupItemController extends BaseCommonController {
    @Autowired
    private CustomerGroupItemService service;



    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "打捆明细";
    }
}

