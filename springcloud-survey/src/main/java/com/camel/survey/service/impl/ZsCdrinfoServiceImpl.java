package com.camel.survey.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.camel.survey.model.ZsCdrinfo;
import com.camel.survey.mapper.ZsCdrinfoMapper;
import com.camel.survey.service.ZsCdrinfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<ZsCdrinfo> selectList(Set<String> agents) {

        return mapper.selectListByAgents(agents);
    }

    @Override
    public Integer selectAvgTime(Integer id) {
        return mapper.selectAvgTime(id);
    }
}
