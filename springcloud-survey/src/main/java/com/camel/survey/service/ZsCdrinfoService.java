package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.ZsCdrinfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
public interface ZsCdrinfoService extends IService<ZsCdrinfo> {
    ZsCdrinfo details(String id);
}
