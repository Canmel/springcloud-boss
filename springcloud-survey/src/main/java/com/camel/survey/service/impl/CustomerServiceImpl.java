package com.camel.survey.service.impl;

import com.camel.core.utils.PaginationUtil;
import com.camel.survey.model.Customer;
import com.camel.survey.mapper.CustomerMapper;
import com.camel.survey.service.CustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-12-09
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private CustomerMapper mapper;

    @Override
    public PageInfo<Customer> selectPage(Customer entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }
}
