package com.camel.oa.service.impl;

import com.camel.oa.model.BaseOaEntity;
import com.camel.oa.model.ZsProject;
import com.camel.oa.model.ZsTalenteder;
import com.camel.oa.mapper.ZsTalentederMapper;
import com.camel.oa.service.ZsTalentederService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 * ┗━┓     ┏━┛
 * ┃     ┃　Code is far away from bug with the animal protecting
 * ┃     ┃   神兽保佑,代码无bug
 * ┃     ┃
 * ┃     ┃
 * ┃     ┃        < 服务实现类>
 * ┃     ┃
 * ┃     ┗━━━━┓   @author baily
 * ┃          ┣┓
 * ┃          ┏┛  @since 1.0
 * ┗┓┓┏━━━━┳┓┏┛
 * ┃┫┫    ┃┫┫    @date 2019-10-30
 * ┗┻┛    ┗┻┛
 */
@Service
public class ZsTalentederServiceImpl extends ServiceImpl<ZsTalentederMapper, ZsTalenteder> implements ZsTalentederService {

    @Autowired
    private ZsTalentederMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo selectPage(ZsTalenteder entity) {
        return applicationToolsUtils.selectPage(entity, () -> {
            mapper.list(entity);
        });
    }
}
