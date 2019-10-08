package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Traffic;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-10-08
 */
public interface TrafficService extends IService<Traffic> {

    List<Traffic> traffic(Integer id);
}
