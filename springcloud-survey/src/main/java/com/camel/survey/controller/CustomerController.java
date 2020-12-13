package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Customer;
import com.camel.survey.model.CustomerForm;
import com.camel.survey.service.CustomerService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.ExcelUtil;
import com.camel.survey.utils.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseCommonController {

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @GetMapping
    public Result index(CustomerForm entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @Autowired
    private CustomerService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVOPS', 'MANAGER')")
    @PostMapping("/import")
    public Result importInfo(@RequestParam("file") MultipartFile file) {
        SysUser currentUser = applicationToolsUtils.currentUser();
        List<Customer> customers = ExcelUtil.readExcelCustomer(file, Customer.class, currentUser);
        service.insertOrUpdateBatch(customers);
        return ResultUtil.success("导入客户信息成功");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVOPS', 'MANAGER')")
    @GetMapping("/export")
    public void export(CustomerForm entity, HttpServletResponse response) {
        HSSFWorkbook wb = new HSSFWorkbook();
        List<Customer> customers =  service.list(entity);
        HSSFSheet sheet = wb.createSheet("客户信息");
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("电话号码(必填)");
        headRow.createCell(1).setCellValue("客户名称");
        for (int i = 1; i<=customers.size();i++) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(customers.get(i-1).getTel());
            row.createCell(1).setCellValue(customers.get(i-1).getUsername());
        }
        ExportExcelUtils.export(wb, "客户信息", response);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "客户信息";
    }
}

