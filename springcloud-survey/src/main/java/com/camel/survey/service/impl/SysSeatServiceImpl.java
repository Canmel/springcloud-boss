package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.mapper.SysSeatMapper;
import com.camel.survey.model.SysSeat;
import com.camel.survey.service.SysSeatService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 坐席表 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-05-30
 */
@Service
public class SysSeatServiceImpl extends ServiceImpl<SysSeatMapper, SysSeat> implements SysSeatService {
    @Autowired
    private SysSeatMapper mapper;

    @Override
    public PageInfo<SysSeat> selectPage(SysSeat entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public Result save(SysSeat entity, OAuth2Authentication oAuth2Authentication) {
        return null;
    }
}
