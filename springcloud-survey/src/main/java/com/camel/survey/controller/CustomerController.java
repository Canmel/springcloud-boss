package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Customer;
import com.camel.survey.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseCommonController {

    @GetMapping
    public Result index(Customer entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @Autowired
    private CustomerService service;

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "客户信息";
    }
}

