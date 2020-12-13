package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Customer;
import com.camel.survey.model.CustomerForm;
import com.camel.survey.model.CustomerGroup;
import com.camel.survey.model.CustomerGroupItem;
import com.camel.survey.service.CustomerGroupItemService;
import com.camel.survey.service.CustomerGroupService;
import com.camel.survey.service.CustomerService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/change")
    public Result change(CustomerGroup entity) {
        CustomerGroup group = service.selectById(entity.getId());
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

