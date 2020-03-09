package com.camel.interviewer.service.impl;

import com.camel.interviewer.model.WxUser;
import com.camel.interviewer.mapper.WxUserMapper;
import com.camel.interviewer.service.WxUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信用户信息 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

}
