package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.survey.model.Args;
import com.camel.survey.model.SysSeat;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * <p>
 * 坐席表 服务类
 * </p>
 *
 * @author baily
 * @since 2020-05-30
 */
public interface SysSeatService extends IService<SysSeat> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<SysSeat> selectPage(SysSeat entity);

    /**
     * 新建
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    Result save(SysSeat entity, OAuth2Authentication oAuth2Authentication);
}
