package com.camel.survey.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsReport;
import com.camel.survey.service.ZsReportService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.ExportExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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


    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @GetMapping
    public Result index(ZsReport report) {
        applicationToolsUtils.currentUser().getSysRoles();
        if(!applicationToolsUtils.hasRole("ROLE_ADMIN")) {
            report.setSharer(applicationToolsUtils.currentUser().getUid());
        }
        return ResultUtil.success(service.list(report));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVOPS', 'MANAGER')")
    @GetMapping("/download")
    public void download(ZsReport report, HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = service.download(report);
        ExportExcelUtils.export(wb, "客户信息", response);
    }

    /**
     * 同意
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVOPS', 'MANAGER')")
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
        if(StringUtils.isNotBlank(report.getOpenid())) {
            String subscribe = service.selectSubscribe(report.getOpenid());
            if(StringUtils.isNotBlank(subscribe)) {
                report.setShareUser(subscribe);
                Integer subscribeUserId = service.subscribeId(subscribe);
                if(!ObjectUtils.isEmpty(subscribeUserId)) {
                    report.setSharer(subscribeUserId);
                }
            }
        }
        return super.save(report);
    }

    /**
     * 重设response
     *
     * @param response
     * @param fileName
     * @return
     */
    private HttpServletResponse setHeader(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        return response;
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

