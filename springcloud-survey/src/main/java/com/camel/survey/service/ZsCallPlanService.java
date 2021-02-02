package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsCallPlan;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

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

    Result save(ZsCallPlan entity);

    void start(Integer id);

    void del(Integer id);

    void uploadNumers(MultipartFile file, Integer id);

    Boolean refresh(Integer id);

    void end(Integer id);
}
