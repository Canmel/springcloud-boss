package com.camel.survey.service;

import com.camel.survey.model.ZsAgencyFee;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-09-28
 */
public interface ZsAgencyFeeService extends IService<ZsAgencyFee> {
    PageInfo<ZsAgencyFee> selectPage(ZsAgencyFee entity);
}
