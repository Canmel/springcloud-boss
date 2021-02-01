package com.camel.survey.service.impl;

import com.camel.core.utils.PaginationUtil;
import com.camel.survey.model.ZsCallPlan;
import com.camel.survey.mapper.ZsCallPlanMapper;
import com.camel.survey.service.ZsCallPlanService;
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
 * @since 2021-02-01
 */
@Service
public class ZsCallPlanServiceImpl extends ServiceImpl<ZsCallPlanMapper, ZsCallPlan> implements ZsCallPlanService {
    @Autowired
    private ZsCallPlanMapper mapper;

    @Override
    public PageInfo<ZsCallPlan> list(ZsCallPlan entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }
}
