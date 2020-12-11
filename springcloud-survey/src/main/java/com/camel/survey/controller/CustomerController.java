package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Customer;
import com.camel.survey.model.CustomerForm;
import com.camel.survey.service.CustomerService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @GetMapping
    public Result index(CustomerForm entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @Autowired
    private CustomerService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVOPS', 'MANAGER')")
    @PostMapping("/import")
    public Result importInfo(@RequestParam("file") MultipartFile file) {
        SysUser currentUser = applicationToolsUtils.currentUser();
        List<Customer> customers = ExcelUtil.readExcelCustomer(file, Customer.class, currentUser);
        service.insertOrUpdateBatch(customers);
        return ResultUtil.success("导入客户信息成功");
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "客户信息";
    }
}

