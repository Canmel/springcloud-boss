package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 客户信息 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-12-09
 */
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {

    List<Customer> list(Customer entity);
}
