package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
@Controller
@RequestMapping("/relSeatQueue")
public class RelSeatQueueController extends BaseCommonController {
    @Override
    public IService getiService() {
        return null;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

