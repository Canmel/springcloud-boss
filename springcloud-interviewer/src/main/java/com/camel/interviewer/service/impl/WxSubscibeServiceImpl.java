package com.camel.interviewer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.interviewer.model.WxSubscibe;
import com.camel.interviewer.mapper.WxSubscibeMapper;
import com.camel.interviewer.service.WxSubscibeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关注用户 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-04
 */
@Service
public class WxSubscibeServiceImpl extends ServiceImpl<WxSubscibeMapper, WxSubscibe> implements WxSubscibeService {
    @Override
    public boolean save(String toUserName) {
        return this.insert(new WxSubscibe(toUserName));
    }

    @Override
    public boolean unsave(String toUserName) {
        Wrapper<WxSubscibe> wrapper = new EntityWrapper<>();
        wrapper.eq("user", toUserName);
        return this.delete(wrapper);
    }
}
