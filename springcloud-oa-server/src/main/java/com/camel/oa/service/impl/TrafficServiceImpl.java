package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.oa.mapper.TrafficMapper;
import com.camel.oa.model.Errand;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Traffic;
import com.camel.oa.service.ErrandService;
import com.camel.oa.service.ImperfectService;
import com.camel.oa.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-10-08
 */
@Service
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, Traffic> implements TrafficService {

    @Autowired
    private ImperfectService imperfectService;

    @Autowired
    private ErrandService errandService;

    @Autowired
    private TrafficService trafficService;


    @Override
    public List<Traffic> traffic(Integer id) {
        Errand errand = errandService.selectById(id);
        List<Traffic> trafficList = null;
        if(!ObjectUtils.isEmpty(errand)) {
            Wrapper<Imperfect> imperfectWrapper = new EntityWrapper<>();
            imperfectWrapper.eq("errand_id", errand.getId());
            Imperfect imperfect = imperfectService.selectOne(imperfectWrapper);

            Wrapper<Traffic> trafficWrapper = new EntityWrapper<>();
            trafficWrapper.eq("imperfect_id", imperfect.getId());
            trafficList = trafficService.selectList(trafficWrapper);
        }
        return trafficList;
    }
}
