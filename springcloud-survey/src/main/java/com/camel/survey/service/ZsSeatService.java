package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.survey.model.ZsSeat;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
public interface ZsSeatService extends IService<ZsSeat> {
    ZsSeat selectByUid(Integer id);
}
