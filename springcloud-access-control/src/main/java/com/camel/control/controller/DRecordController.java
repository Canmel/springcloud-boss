package com.camel.control.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.control.model.DRecord;
import com.camel.control.service.DRecordService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@Controller
@RequestMapping("/record")
public class DRecordController extends BaseCommonController {
    @Autowired
    private DRecordService service;

    @PostMapping("/add")
    public Result add(DRecord record, HttpServletRequest request) {
        return ResultUtil.success("success");
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

