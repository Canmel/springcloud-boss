package com.camel.realname.controller;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.model.ApplyNumber;
import com.camel.realname.model.ApproveInfo;
import com.camel.realname.model.TelProtection;
import com.camel.realname.service.ApplyNumberService;
import com.camel.realname.service.TelProtectionService;
import com.camel.realname.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 前端控制器
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

    @Autowired
    private TelProtectionService telService;

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
        if (ObjectUtil.isNotEmpty(entity.getId())) {
            return ResultUtil.success(service.selectById(entity.getId()));
        }
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
        if (ObjectUtils.isEmpty(entity)) {
            entity = new ApplyNumber();
            entity.buildCode();
            entity.setCreatorId(sysUser.getUid());
        }
        String[] fileNames = file.getOriginalFilename().split("\\.");
        if (ArrayUtil.isNotEmpty(fileNames)) {
            String type = fileNames[fileNames.length - 1];

            if (fileType.equals("applySheet")) {
                if (!isExcel(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"客户申请表请上传excel文件");
                }
                JSONObject object = service.upload(file);
                entity.setApplySheet(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("license")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"单位资质请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setLicense(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("cardLegal")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"法人身份证请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setCardLegal(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("cardLegalH")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"法人手持身份证请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setCardLegalH(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("cardAgent")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"经办人身份证请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setCardAgent(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("handAgent")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"经办人身份证手持照片请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setHandAgent(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("cardUser")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"使用人身份证请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setCardUser(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("enterPromise")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"电信入网承诺书请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setEnterPromise(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }
            if (fileType.equals("applyLetter")) {
                if (!isPic(file)){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"号码申请公函请上传图片(PNG,JPG格式)文件");
                }
                JSONObject object = service.upload(file);
                entity.setApplyLetter(object.getString("key"));
                return ResultUtil.success(service.insertOrUpdate(entity));
            }

        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "上传失败");
    }

    @GetMapping("/getName/{tel}")
    public Result getFinalName(@PathVariable("tel") String tel){
        return telService.getFinalName(tel);
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
        if (ArrayUtil.isNotEmpty(fileNames)) {
            String type = fileNames[fileNames.length - 1];
            return excelFileSuf.indexOf(type.toUpperCase()) > -1;
        }
        return false;
    }

    public boolean isPic(MultipartFile file) {
        String[] fileNames = file.getOriginalFilename().split("\\.");
        if (ArrayUtil.isNotEmpty(fileNames)) {
            String type = fileNames[fileNames.length - 1];
            return picFileSuf.indexOf(type.toUpperCase()) > -1;
        }
        return false;
    }
}

