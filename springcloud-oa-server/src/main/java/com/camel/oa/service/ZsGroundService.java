package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.oa.model.ZsGround;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface ZsGroundService extends IService<ZsGround> {
    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    PageInfo<ZsGround> selectPage(ZsGround entity);

    /**
     * 保存
     *
     * @param oAuth2Authentication
     * @return
     */
    Result save(ZsGround entity, OAuth2Authentication oAuth2Authentication);
}
