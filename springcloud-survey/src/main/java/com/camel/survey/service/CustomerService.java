package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.Customer;
import com.camel.survey.model.CustomerForm;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author baily
 * @since 2020-12-09
 */
public interface CustomerService extends IService<Customer> {
    PageInfo<Customer> selectPage(Customer entity);

    List<Customer> list(CustomerForm entity);
}
