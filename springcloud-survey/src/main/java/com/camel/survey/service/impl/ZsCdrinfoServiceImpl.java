package com.camel.survey.service.impl;

import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.mapper.ZsCdrinfoMapper;
import com.camel.survey.service.ZsCdrinfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
@Service
public class ZsCdrinfoServiceImpl extends ServiceImpl<ZsCdrinfoMapper, ZsCdrinfo> implements ZsCdrinfoService {
    @Autowired
    private ZsCdrinfoMapper mapper;
    @Override
    public ZsCdrinfo details(String id) {
        return mapper.details(id);
    }
}
