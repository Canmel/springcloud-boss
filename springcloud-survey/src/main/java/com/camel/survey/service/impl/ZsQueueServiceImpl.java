package com.camel.survey.service.impl;

import com.camel.core.utils.PaginationUtil;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQueue;
import com.camel.survey.mapper.ZsQueueMapper;
import com.camel.survey.service.ZsQueueService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
@Service
public class ZsQueueServiceImpl extends ServiceImpl<ZsQueueMapper, ZsQueue> implements ZsQueueService {
    @Autowired
    private ZsQueueMapper mapper;

    @Override
    public PageInfo<ZsOption> selectPage(ZsQueue entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }
}
