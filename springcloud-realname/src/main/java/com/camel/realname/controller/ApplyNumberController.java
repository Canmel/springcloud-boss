package com.camel.realname.controller;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static final List<String> excelFileSuf = ListUtil.toList("XLS", "XLSX");
    public static final List<String> picFileSuf = ListUtil.toList("PNG", "JPG");

    @Autowired
    private ApplyNumberService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @GetMapping("/apply/{id}")
    public Result apply(@PathVariable Integer id) {
        return service.apply(id);
    }

    @GetMapping("/index")
    public Result index(ApplyNumber entity) {
        SysUser current = applicationToolsUtils.currentUser();
        entity.setCreatorId(current.getUid());
        return ResultUtil.success(service.list(entity));
    }

    @GetMapping("/applying")
    public Result applying(ApplyNumber entity) {
        if(ObjectUtil.isNotEmpty(entity.getId())) {
            return ResultUtil.success(service.selectById(entity.getId()));
        }
        SysUser user = applicationToolsUtils.currentUser();
        Wrapper wrapper = new EntityWrapper();
        wrapper.gt("status", 1);
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

           if(fileType.equals("applySheet") && isExcel(file)) {
               JSONObject object = service.upload(file);
               entity.setApplySheet(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
           if(fileType.equals("license") && isPic(file)) {
               JSONObject object = service.upload(file);
               entity.setLicense(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
           if(fileType.equals("cardLegal") && isPic(file)) {
               JSONObject object = service.upload(file);
               entity.setCardLegal(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
           if(fileType.equals("cardAgent") && isPic(file)) {
               JSONObject object = service.upload(file);
               entity.setCardAgent(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
           if(fileType.equals("handAgent") && isPic(file)) {
               JSONObject object = service.upload(file);
               entity.setHandAgent(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
           if(fileType.equals("cardUser") && isPic(file)) {
               JSONObject object = service.upload(file);
               entity.setCardUser(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
           if(fileType.equals("enterPromise") && isPic(file)) {
               JSONObject object = service.upload(file);
               entity.setEnterPromise(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
           if(fileType.equals("applyLetter") && isPic(file)) {
               JSONObject object = service.upload(file);
               entity.setApplyLetter(object.getString("key"));
               return ResultUtil.success(service.insertOrUpdate(entity));
           }
        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "上传失败");
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

    public boolean isExcel(MultipartFile file) {
        String[] fileNames = file.getOriginalFilename().split("\\.");
        if(ArrayUtil.isNotEmpty(fileNames)) {
            String type = fileNames[fileNames.length - 1];
            return excelFileSuf.indexOf(type.toUpperCase()) > -1;
        }
        return false;
    }

    public boolean isPic(MultipartFile file) {
        String[] fileNames = file.getOriginalFilename().split("\\.");
        if(ArrayUtil.isNotEmpty(fileNames)) {
            String type = fileNames[fileNames.length - 1];
            return picFileSuf.indexOf(type.toUpperCase()) > -1;
        }
        return false;
    }
}

