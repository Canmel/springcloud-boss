package com.camel.survey.mapper;

import com.camel.survey.model.CustomerGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 客户组 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-12-13
 */
@Repository
public interface CustomerGroupMapper extends BaseMapper<CustomerGroup> {
    List<CustomerGroup> list(CustomerGroup entity);
}
