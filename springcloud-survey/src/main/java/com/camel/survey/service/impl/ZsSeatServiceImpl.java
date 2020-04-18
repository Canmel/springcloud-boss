package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.mapper.ZsSeatMapper;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
@Service
public class ZsSeatServiceImpl extends ServiceImpl<ZsSeatMapper, ZsSeat> implements ZsSeatService {

    @Autowired
    public ZsSeatMapper mapper;

    @Override
    public ZsSeat selectByUid(Integer id) {
        Wrapper<ZsSeat> zsSeatWrapper = new EntityWrapper<>();
        zsSeatWrapper.eq("uid", id);
        return selectOne(zsSeatWrapper);
    }

    @Override
    public ZsSeat selectLastByUser(Integer id) {
        return mapper.selectLastByUser(id);
    }
}
