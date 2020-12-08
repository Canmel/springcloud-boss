package com.camel.survey.service;

import com.camel.core.model.ZsAgency;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-12-08
 */
public interface ZsAgencyService extends IService<ZsAgency> {
    PageInfo<ZsAgency> selectPage(ZsAgency entity);
}
