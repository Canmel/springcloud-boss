package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsPhoneInformation;
import com.camel.survey.service.ZsPhoneInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-06-10
 */
@RestController
@RequestMapping("/zsPhoneInformation")
public class ZsPhoneInformationController extends BaseCommonController {

    @Autowired
    private ZsPhoneInformationService service;

    @GetMapping
    public Result index(ZsPhoneInformation zsPhoneInformation) {
        return ResultUtil.success(service.pageQuery(zsPhoneInformation));
    }

    /**
     * 号码信息通过导入新增
     * @param file
     * @return
     */
    @PostMapping("/importPhoneInformation/{id}")
    public Result importPhoneInformation(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer surveyId) {
        service.importPhoneInformation(file, surveyId);
        return ResultUtil.success("成功");
    }

    /**
     * 根据号码获取详情
     */
    @AuthIgnore
    @GetMapping("/selectByMobileAndSurvey")
    public Result details(ZsPhoneInformation zsPhoneInformation) {
        return ResultUtil.success(service.selectByMobileAndSurvey(zsPhoneInformation));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "号码信息";
    }
}

