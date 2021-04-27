package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.SysCompany;
import com.camel.survey.service.SysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2021-04-27
 */
@RestController
@RequestMapping("/sysCompany")
public class SysCompanyController extends BaseCommonController {

    @Autowired
    private SysCompanyService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Result index(SysCompany entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Result save(SysCompany entity) {
        return super.save(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Result update(@RequestBody SysCompany entity) {
        return super.update(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        return super.details(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Result del(@PathVariable Integer id) {
        return super.delete(id);
    }


    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "公司";
    }
}

