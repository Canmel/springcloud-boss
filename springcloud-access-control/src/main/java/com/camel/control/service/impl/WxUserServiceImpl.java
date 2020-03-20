package com.camel.control.service.impl;

import com.camel.control.model.WxUser;
import com.camel.control.mapper.WxUserMapper;
import com.camel.control.service.WxUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信用户信息 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

}
