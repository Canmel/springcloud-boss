package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Trip;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-07-12
 */
public interface TripService extends IService<Trip> {
    List<Trip> getTrip(Trip trip);

    List<Trip> getTripByImperfect(Integer id);
}
