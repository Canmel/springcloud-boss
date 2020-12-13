package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.model.Customer;
import com.camel.survey.model.CustomerForm;
import com.camel.survey.model.CustomerGroup;
import com.camel.survey.model.CustomerGroupItem;
import com.camel.survey.service.CustomerGroupItemService;
import com.camel.survey.service.CustomerGroupService;
import com.camel.survey.service.CustomerService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 客户组 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/customerGroup")
public class CustomerGroupController extends BaseCommonController {
    @Autowired
    private CustomerGroupService service;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerGroupItemService itemService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @GetMapping
    public Result index(CustomerGroup entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVOPS', 'MANAGER')")
    @GetMapping("/export/{id}")
    public void export(@PathVariable Integer id, HttpServletResponse response) {
        Wrapper<CustomerGroupItem> itemWrapper = new EntityWrapper<>();
        itemWrapper.eq("group_id", id);

        HSSFWorkbook wb = new HSSFWorkbook();
        List<CustomerGroupItem> customers =  itemService.selectList(itemWrapper);
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

    /**
     * 获取能用的号码打捆库
     * @return
     */
    @GetMapping("/active")
    public Result active() {
        Wrapper<CustomerGroup> customerGroupWrapper = new EntityWrapper<>();
        customerGroupWrapper.eq("state", ZsYesOrNo.YES.getCode());
        return ResultUtil.success(service.selectList(customerGroupWrapper));
    }

    @GetMapping("/change")
    public Result change(CustomerGroup entity) {
        CustomerGroup group = service.selectById(entity.getId());
        if(group.getState().equals(ZsYesOrNo.YES)) {
            group.setState(ZsYesOrNo.NO);
        }else {
            group.setState(ZsYesOrNo.YES);
        }
        service.updateById(group);
        return ResultUtil.success("切换成功");
    }

    @GetMapping("/save")
    public Result save(CustomerForm entity) {
        Wrapper<CustomerGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("name", entity.getVersionName());
        int count =service.selectCount(wrapper);
        if(count > 0) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "分组已存在");
        }
        CustomerGroup customerGroup = new CustomerGroup(entity.getVersionName());
        customerGroup.setCreatorId(applicationToolsUtils.currentUser().getUid());
        service.insert(customerGroup);
        List<Customer> customerList = customerService.list(entity);
        List<CustomerGroupItem> items = new ArrayList<>();
        for (Customer c: customerList) {
            items.add(new CustomerGroupItem(c, customerGroup.getId()));
        }

        itemService.insertBatch(items, 200);

        return ResultUtil.success("保存成功！");
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "客户组";
    }
}

