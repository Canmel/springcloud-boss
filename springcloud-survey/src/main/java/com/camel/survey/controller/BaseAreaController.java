package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.BaseArea;
import com.camel.survey.service.BaseAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 统一地区库 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/baseArea")
public class BaseAreaController extends BaseCommonController {

    @Autowired
    private BaseAreaService service;

    @AuthIgnore
    @GetMapping("/{id}")
    public Result getList(@PathVariable Integer id) {
        Wrapper<BaseArea> areaWrapper = new EntityWrapper<>();
        areaWrapper.eq("parentid", id);
        return ResultUtil.success(service.selectList(areaWrapper));
    }

    @Override
    public IService getiService() {
        return null;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

