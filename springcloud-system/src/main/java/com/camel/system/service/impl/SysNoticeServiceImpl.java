package com.camel.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.system.mapper.SysNoticeMapper;
import com.camel.system.model.SysMenu;
import com.camel.system.model.SysNotice;
import com.camel.system.service.SysNoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-09-29
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Autowired
    private SysNoticeMapper mapper;

    @Override
    public PageInfo<SysNotice> selectPage(SysNotice sysNotice) {
        PageInfo pageInfo = PaginationUtil.startPage(sysNotice, () -> {
            mapper.list(sysNotice);
        });
        return pageInfo;
    }

    @Override
    public boolean delete(Serializable serializable) {
        SysNotice sysMenu = new SysNotice((Integer) serializable, 0);
        return mapper.updateById(sysMenu) > -1;
    }
}
