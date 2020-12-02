package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.AreaAddress;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.AreaAddressService;
import com.camel.survey.utils.ExcelUtil;
import com.camel.survey.utils.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Area;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/areaAddress")
public class AreaAddressController extends BaseCommonController {

    @Autowired
    private AreaAddressService service;

    @GetMapping
    public Result index(AreaAddress entity) {
        return ResultUtil.success(service.pageQuery(entity));
    }

    @DeleteMapping("/{id}")
    public Result del(@PathVariable Integer id) {
        Wrapper<AreaAddress> areaAddressWrapper = new EntityWrapper<>();
        areaAddressWrapper.eq("area_id", id);
        service.delete(areaAddressWrapper);
        return ResultUtil.success("情况指定区域内的地址成功");
    }

    @AuthIgnore
    @GetMapping("/match")
    public Result match(String text, Integer areaId) {
        return ResultUtil.success(service.selectMatch(text, areaId));
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, Integer areaId) {
        List<AreaAddress> areaAddresses = ExcelUtil.readExcelObject(file, AreaAddress.class);
        for (AreaAddress areaAddress: areaAddresses) {
            areaAddress.setName(areaAddress.getArea() + areaAddress.getAddress());
            areaAddress.setPinyin(FileTransfer.getPinyin(areaAddress.getName(), ""));
            areaAddress.setInitials(FileTransfer.getPinyinInitials(areaAddress.getName()));
            areaAddress.setAreaId(areaId);
        }
        service.insertBatch(areaAddresses, 500);
        return ResultUtil.success("导入地址信息成功");
    }

    @Override
    public IService getiService() {
        return null;
    }

    @Override
    public String getMouduleName() {
        return null;
    }
}

