package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.enums.ErrandStatus;
import com.camel.oa.mapper.ErrandMapper;
import com.camel.oa.model.Errand;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Route;
import com.camel.oa.model.Trip;
import com.camel.oa.service.ErrandService;
import com.camel.oa.service.ImperfectService;
import com.camel.oa.service.RouteService;
import com.camel.oa.service.TripService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <出差服务实现>
 * @author baily
 * @since 1.0
 * @date 2019/7/8
 **/
@Service
public class ErrandServiceImpl extends ServiceImpl<ErrandMapper, Errand> implements ErrandService {
    @Autowired
    private ErrandMapper mapper;

    @Autowired
    private TripService tripService;

    @Autowired
    private ImperfectService imperfectService;

    @Autowired
    private RouteService routeService;

    @Override
    public PageInfo<Errand> selectPage(Errand entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public List<Errand> imperfect(Integer id) {
        return mapper.imperfect(id, ErrandStatus.APPLY_SUCCESS.getValue());
    }

    @Override
    public List<Trip> trips(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("errand_id", id);
        List<Imperfect> imperfects = imperfectService.selectList(wrapper);
        return tripService.getTripByImperfect(imperfects.get(0).getId());
    }

    @Override
    public Route route(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("errand_id", id);
        List<Imperfect> imperfects = imperfectService.selectList(wrapper);
        if(ObjectUtils.isEmpty(imperfects)) {
            return null;
        }else {
            return routeService.getByImperfectId(imperfects.get(0).getId());
        }
    }
}
