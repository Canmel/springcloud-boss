package com.camel.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.system.mapper.SysLogMapper;
import com.camel.core.model.SysLog;
import com.camel.system.service.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-09
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Autowired
    private SysLogMapper mapper;

    @Override
    public PageInfo<SysLog> pageQuery(SysLog entity) {
        PageInfo pageInfo = PageHelper.startPage(entity.getPageNum(), entity.getPageSize()).doSelectPageInfo(() -> mapper.list(entity));
        return pageInfo;
    }
}
