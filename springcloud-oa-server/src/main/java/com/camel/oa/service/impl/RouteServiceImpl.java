package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.mapper.RouteMapper;
import com.camel.oa.model.Route;
import com.camel.oa.service.RouteService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-07-18
 */
@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService {
    @Autowired
    private RouteMapper mapper;

    @Override
    public PageInfo<Route> selectPage(Route route) {
        PageInfo pageInfo = PaginationUtil.startPage(route, () -> {
            mapper.list(route);
        });
        return pageInfo;
    }

    @Override
    public Route getByImperfectId(Integer id) {
        return mapper.selectOne(new Route(id));
    }

    @Override
    public Route selectOne(Route route) {
        return mapper.selectOne(route);
    }
}
