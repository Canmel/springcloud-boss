package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsSurveySignResult;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.model.CustomerGroup;
import com.camel.survey.mapper.CustomerGroupMapper;
import com.camel.survey.model.ZsSign;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.CustomerGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户组 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-12-13
 */
@Service
public class CustomerGroupServiceImpl extends ServiceImpl<CustomerGroupMapper, CustomerGroup> implements CustomerGroupService {
    @Autowired
    private CustomerGroupMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<CustomerGroup> selectPage(CustomerGroup entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            this.list(entity);
        });
        List<CustomerGroup> list = pageInfo.getList();
        list.forEach(e -> {
            applicationToolsUtils.allUsers().forEach(sysUser -> {
                if (sysUser.getUid().equals(e.getCreator().getUid())) {
                   e.setCreator(sysUser);
                }
            });
        });
        return pageInfo;
    }

    @Override
    public List<CustomerGroup> list(CustomerGroup entity) {
        return mapper.list(entity);
    }
}
