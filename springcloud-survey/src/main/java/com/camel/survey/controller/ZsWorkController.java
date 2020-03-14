package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.service.ZsWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 访员工作记录 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/zsWork")
public class ZsWorkController extends BaseCommonController {
    @Autowired
    private ZsWorkService service;

    @PostMapping("/upload")
    public Result upLoad(@RequestParam MultipartFile file) {
        return ResultUtil.success("上传成功");
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "工作记录";
    }
}

