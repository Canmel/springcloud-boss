package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/areaAddress")
public class AreaAddressController extends BaseCommonController {

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) {
        return ResultUtil.success(file.getSize());
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

