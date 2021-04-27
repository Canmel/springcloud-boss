package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.SysCompany;
import com.camel.survey.model.SysSeat;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author baily
 * @since 2021-04-27
 */
public interface SysCompanyService extends IService<SysCompany> {
    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    PageInfo<SysCompany> selectPage(SysCompany entity);
}
