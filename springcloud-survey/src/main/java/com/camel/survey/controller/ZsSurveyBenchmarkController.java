package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsSurveyBenchmark;
import com.camel.survey.service.ZsSurveyBenchmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 项目基准 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-07-08
 */
@RestController
@RequestMapping("/zsSurveyBenchmark")
public class ZsSurveyBenchmarkController extends BaseCommonController {
    @Autowired
    private ZsSurveyBenchmarkService service;

    @PostMapping
    public Result save(ZsSurveyBenchmark entity) {
        return super.save(entity);
    }

    @GetMapping
    public Result index(ZsSurveyBenchmark entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @GetMapping("/pid/{id}")
    public Result selectBuPids(@PathVariable Integer id) {
        return ResultUtil.success(service.selectListByPids(id));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "项目基准";
    }
}

