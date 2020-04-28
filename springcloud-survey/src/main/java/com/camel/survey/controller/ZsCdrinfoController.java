package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.service.ZsCdrinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/zsCdrinfo")
public class ZsCdrinfoController extends BaseCommonController {

    @Autowired
    private ZsCdrinfoService service;

    @AuthIgnore
    @PostMapping
    public String save(@RequestBody Map<String, ZsCdrinfo[]> params) {
        try{
            if(service.insertBatch(CollectionUtils.arrayToList(params.get("cdr")))) {
                return "success";
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
        return "error";
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "话单推送";
    }
}

