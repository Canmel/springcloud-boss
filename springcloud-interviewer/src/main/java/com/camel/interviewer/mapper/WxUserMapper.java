package com.camel.interviewer.mapper;

import com.camel.interviewer.model.WxUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 微信用户信息 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
public interface WxUserMapper extends BaseMapper<WxUser> {
    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<WxUser> list(WxUser entity);
}
