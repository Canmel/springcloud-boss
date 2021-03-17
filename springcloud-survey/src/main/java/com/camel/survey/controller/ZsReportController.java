package com.camel.survey.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsReport;
import com.camel.survey.service.ZsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baily
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/zsReport")
public class ZsReportController extends BaseCommonController {
    @Autowired
    private ZsReportService service;


    @GetMapping
    public Result index(ZsReport report) {
        return ResultUtil.success(service.list(report));
    }

    @GetMapping("/agree/{id}")
    public Result agree(@PathVariable Integer id) {
        return ResultUtil.success(service.agree(id));
    }


    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @AuthIgnore
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        JSONObject jsonObject = service.save(file);
        return ResultUtil.success(service.url(jsonObject.getString("hash")), jsonObject.getString("hash"));
    }

    @AuthIgnore
    @PostMapping
    public Result save(@RequestBody ZsReport report) {
        return super.save(report);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "兼职报名";
    }
}

