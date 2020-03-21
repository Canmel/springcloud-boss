package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.survey.model.ZsCashApply;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 提现省钱 服务类
 * </p>
 *
 * @author baily
 * @since 2020-03-21
 */
public interface ZsCashApplyService extends IService<ZsCashApply> {

    Result apply(ZsCashApply apply);
}
