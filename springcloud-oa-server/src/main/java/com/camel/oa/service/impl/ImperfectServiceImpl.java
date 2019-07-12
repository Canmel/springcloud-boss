package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.oa.mapper.ImperfectMapper;
import com.camel.oa.model.Imperfect;
import com.camel.oa.service.ImperfectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-07-12
 */
@Service
public class ImperfectServiceImpl extends ServiceImpl<ImperfectMapper, Imperfect> implements ImperfectService {

    @Autowired
    private ImperfectMapper mapper;

    @Override
    public boolean valid(Integer errandId) {
        Wrapper wrapper = new EntityWrapper<Imperfect>();
        wrapper.eq("errand_id", errandId);
        return mapper.selectCount(wrapper) == 0;
    }
}
