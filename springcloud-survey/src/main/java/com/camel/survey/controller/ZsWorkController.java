package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
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

    @GetMapping
    public Result index(ZsWork entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
    }

    @PostMapping("/upload")
    public Result upLoad(@RequestParam MultipartFile file) {
        service.importExcel(file);
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

