package com.camel.survey.service;

import com.camel.survey.model.ZsCallPlan;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2021-02-01
 */
public interface ZsCallPlanService extends IService<ZsCallPlan> {

    PageInfo<ZsCallPlan> list(ZsCallPlan entity);
}
