package com.camel.survey.service.impl;

import com.camel.core.utils.PaginationUtil;
import com.camel.survey.model.SysCompany;
import com.camel.survey.mapper.SysCompanyMapper;
import com.camel.survey.service.SysCompanyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2021-04-27
 */
@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements SysCompanyService {
    @Autowired
    private SysCompanyMapper mapper;

    @Override
    public PageInfo<SysCompany> selectPage(SysCompany entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }
}
