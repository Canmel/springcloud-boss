package com.camel.survey.service.impl;

import com.camel.core.utils.PaginationUtil;
import com.camel.core.model.ZsAgency;
import com.camel.survey.mapper.ZsAgencyMapper;
import com.camel.survey.service.ZsAgencyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-12-08
 */
@Service("zsAgencyService")
public class ZsAgencyServiceImpl extends ServiceImpl<ZsAgencyMapper, ZsAgency> implements ZsAgencyService {
    @Autowired
    private ZsAgencyMapper mapper;

    @Override
    public PageInfo<ZsAgency> selectPage(ZsAgency entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public List<ZsAgency> list() {
        return mapper.list(null);
    }
}
