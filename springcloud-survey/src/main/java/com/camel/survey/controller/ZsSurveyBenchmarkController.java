package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.model.ZsSurveyBenchmark;
import com.camel.survey.service.ZsSurveyBenchmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
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
        if(isExist(entity)) {
            throw new SourceDataNotValidException("当日基准量已经存在");
        }
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

    /**
     * 判断是不是已经存在
     * @param entity
     * @return
     */
    public boolean isExist(ZsSurveyBenchmark entity) {
        if(ObjectUtils.isEmpty(entity.getWorkDate()) || ObjectUtils.isEmpty(entity.getPid())) {
            return false;
        }
        Wrapper wrapper = new EntityWrapper<ZsSurveyBenchmark>();
        wrapper.eq("pid", entity.getPid());
        wrapper.eq("work_date", entity.getWorkDate());
        return service.selectCount(wrapper) > 0;
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

