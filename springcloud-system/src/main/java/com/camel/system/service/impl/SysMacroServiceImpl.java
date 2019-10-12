package com.camel.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.enums.MacroStatus;
import com.camel.system.mapper.SysMacroMapper;
import com.camel.core.model.SysMacro;
import com.camel.system.service.SysMacroService;
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
 * @since 2019-05-15
 */
@Service
public class SysMacroServiceImpl extends ServiceImpl<SysMacroMapper, SysMacro> implements SysMacroService {
    @Autowired
    private SysMacroMapper mapper;

    @Override
    public PageInfo<SysMacro> selectPage(SysMacro entity) {
        entity.setStatus(MacroStatus.NORMAL.getCode());
        PageInfo pageInfo = PageHelper.startPage(entity.getPageNum(), entity.getPageSize()).doSelectPageInfo(() -> mapper.list(entity));
        return pageInfo;
    }
}
