package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsCashApply;
import com.camel.survey.service.ZsCashApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提现省钱 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-21
 */
@RestController
@RequestMapping("/zsCashApply")
public class ZsCashApplyController extends BaseCommonController {
    @Autowired
    private ZsCashApplyService service;

    @AuthIgnore
    @PostMapping
    public Result save(ZsCashApply apply) {
        return service.apply(apply);
    }

    @Override
    public IService getiService() {
        return this.service;
    }

    @Override
    public String getMouduleName() {
        return "提现申请";
    }
}

