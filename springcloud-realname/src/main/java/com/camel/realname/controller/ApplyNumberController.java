package com.camel.realname.controller;


import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ApplicationUtils;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.enums.FileType;
import com.camel.realname.model.ApplyNumber;
import com.camel.realname.service.ApplyNumberService;
import com.camel.realname.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/applyNumber")
public class ApplyNumberController extends BaseCommonController {
    @Autowired
    private ApplyNumberService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @GetMapping("/index")
    public Result index(ApplyNumber entity) {
        SysUser current = applicationToolsUtils.currentUser();
        entity.setCreatorId(current.getUid());
        return ResultUtil.success(service.list(entity));
    }

    @GetMapping("/applying")
    public Result applying(ApplyNumber entity) {
        SysUser user = applicationToolsUtils.currentUser();
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("status", 1);
        wrapper.eq("creator", user.getUid());
        return ResultUtil.success(service.selectOne(wrapper));
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, String fileType) {
        SysUser sysUser = applicationToolsUtils.currentUser();
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("status", 1);
        wrapper.eq("creator", sysUser.getUid());
        ApplyNumber entity = service.selectOne(wrapper);
        if(ObjectUtils.isEmpty(entity)) {
            entity = new ApplyNumber();
            entity.buildCode();
            entity.setCreatorId(sysUser.getUid());
        }
        String[] fileNames = file.getOriginalFilename().split("\\.");
        if(ArrayUtil.isNotEmpty(fileNames)) {
           String type = fileNames[fileNames.length - 1];
           if(type.toUpperCase().equals("XLS") || type.toUpperCase().equals("XLSX")) {
               JSONObject object = service.upload(file);
               if(fileType.equals("applySheet")) {
                   entity.setApplySheet(object.getString("key"));
               }
               if(fileType.equals("license")) {
                   entity.setLicense(object.getString("key"));
               }
               if(fileType.equals("cardLegal")) {
                   entity.setCardLegal(object.getString("key"));
               }
               if(fileType.equals("cardAgent")) {
                   entity.setCardAgent(object.getString("key"));
               }
               if(fileType.equals("handAgent")) {
                   entity.setHandAgent(object.getString("key"));
               }
               if(fileType.equals("cardUser")) {
                   entity.setCardUser(object.getString("key"));
               }
               if(fileType.equals("enterPromise")) {
                   entity.setEnterPromise(object.getString("key"));
               }
               if(fileType.equals("applyLetter")) {
                   entity.setApplyLetter(object.getString("key"));
               }
               return ResultUtil.success(service.insertOrUpdate(entity));
           } else{
               return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "上传失败");
           }
        }
        return ResultUtil.success("上传成功");
    }

    @PostMapping
    public Result save(ApplyNumber entity) {
        return ResultUtil.success("");
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "外呼号码";
    }
}

