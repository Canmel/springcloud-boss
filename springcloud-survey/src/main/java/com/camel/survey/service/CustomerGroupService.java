package com.camel.survey.service;

import com.camel.survey.model.CustomerGroup;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 客户组 服务类
 * </p>
 *
 * @author baily
 * @since 2020-12-13
 */
public interface CustomerGroupService extends IService<CustomerGroup> {
    PageInfo<CustomerGroup> selectPage(CustomerGroup entity);

    List<CustomerGroup> list(CustomerGroup entity);
}
