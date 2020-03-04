package com.camel.interviewer.controller;


import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

/**
 * <p>
 * 关注用户 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-04
 */
@Controller
@RequestMapping("/wxSubscibe")
public class WxSubscibeController extends BaseCommonController {
    @Override
    public IService getiService() {
        return null;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

