package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Route;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RouteService extends IService<Route> {
    PageInfo<Route> selectPage(Route route);

    Route getByImperfectId(Integer id);

    Route selectOne(Route route);
}
