package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.oa.mapper.TripMapper;
import com.camel.oa.model.Trip;
import com.camel.oa.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * <车船票>
 * @author baily
 * @since 1.0
 * @date 2019/7/16
 **/
@Service
public class TripServiceImpl extends ServiceImpl<TripMapper, Trip> implements TripService {

    @Autowired
    private TripMapper mapper;

    /**
     * 获取合适的车船票
     * @param trip
     * @return
     */
    @Override
    public List<Trip> getTrip(Trip trip) {
        return null;
    }

    @Override
    public List<Trip> getTripByImperfect(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("imperfect_id", id);
        return mapper.selectList(wrapper);
    }
}
