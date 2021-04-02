package com.camel.interviewer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.interviewer.feign.SpringCloudSystemFeignClient;
import com.camel.interviewer.mapper.WxSubscibeMapper;
import com.camel.interviewer.model.WxSubscibe;
import com.camel.interviewer.model.WxUser;
import com.camel.interviewer.mapper.WxUserMapper;
import com.camel.interviewer.service.WxUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    private WxSubscibeMapper wxSubscibeMapper;

    @Autowired
    private SpringCloudSystemFeignClient springCloudSystemFeignClient;

    @Override
    public WxUser selectByOpenid(String openid) {
        WxUser wxUser = wxUserMapper.selectOne(new WxUser(openid));
        return ObjectUtils.isEmpty(wxUser) ? new WxUser(openid) : wxUser;
    }

    @Override
    public void updateSystem(SysUser sysUser) {
        springCloudSystemFeignClient.newNormal(sysUser);
    }

    @Override
    public PageInfo<WxUser> pageQuery(WxUser wxUser) {
        if(ObjectUtil.isNotNull(wxUser.getTjUser()) && ObjectUtil.isNotNull(wxUser.getTjUser().getUsername())) {
            Wrapper<WxUser> wxUserWrapper = new EntityWrapper<>();
            wxUserWrapper.like("username", wxUser.getTjUser().getUsername());
            List<WxUser> wxUsers = wxUserMapper.selectList(wxUserWrapper);
            wxUser.setOpenIds(wxUsers.stream().map(WxUser::getOpenid).collect(Collectors.toList()));
        }
        PageInfo pageInfo = PaginationUtil.startPage(wxUser, () -> {
            wxUserMapper.list(wxUser);
        });
        return pageInfo;
    }

    @Override
    public List<Map<String, String>> selectRecommends(String openid) {
        if(StringUtils.isEmpty(openid)) {
            return new ArrayList<>();
        }
        return wxUserMapper.selectRecommends(openid);
    }
}
