package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.HangUp;
import com.camel.survey.service.HangUpService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/hangUp")
public class HangUpController extends BaseCommonController {
    @Autowired
    private HangUpService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @PostMapping
    public Result save(HangUp hangUp) {
        SysUser currentUser = applicationToolsUtils.currentUser();
        hangUp.setCreator(currentUser);
        hangUp.setCreatorId(currentUser.getUid());
        Result result = ResultUtil.success("-------");
        try {
            result = super.save(hangUp);
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "挂断原因";
    }
}

