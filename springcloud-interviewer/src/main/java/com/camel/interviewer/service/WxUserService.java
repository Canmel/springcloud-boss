package com.camel.interviewer.service;

import com.camel.core.model.SysUser;
import com.camel.interviewer.model.WxUser;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 微信用户信息 服务类
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
public interface WxUserService extends IService<WxUser> {

    WxUser selectByOpenid(String openid);

    void updateSystem(SysUser sysUser);

    PageInfo<WxUser> pageQuery(WxUser wxUser);

    List<Map<String, String>> selectRecommends(String openid);
}
