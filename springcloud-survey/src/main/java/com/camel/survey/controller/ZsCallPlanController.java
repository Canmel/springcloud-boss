package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsCallPlan;
import com.camel.survey.service.ZsCallPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/zsCallPlan")
public class ZsCallPlanController extends BaseCommonController {
    @Autowired
    private ZsCallPlanService service;

    @GetMapping
    public Result index(ZsCallPlan entity) {
        return ResultUtil.success(service.list(entity));
    }

    @PostMapping
    public Result save(ZsCallPlan entity) {
        return ResultUtil.success(super.save(entity));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "外呼计划";
    }
}

