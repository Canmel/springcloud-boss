package com.camel.survey.service.impl;

import com.camel.core.utils.PaginationUtil;
import com.camel.survey.model.ZsAgencyFee;
import com.camel.survey.mapper.ZsAgencyFeeMapper;
import com.camel.survey.service.ZsAgencyFeeService;
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
 * @since 2020-09-28
 */
@Service
public class ZsAgencyFeeServiceImpl extends ServiceImpl<ZsAgencyFeeMapper, ZsAgencyFee> implements ZsAgencyFeeService {
    @Autowired
    private ZsAgencyFeeMapper mapper;

    @Override
    public PageInfo<ZsAgencyFee> selectPage(ZsAgencyFee entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }
}
