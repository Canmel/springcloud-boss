package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.ZsSurveyBenchmark;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 项目基准 服务类
 * </p>
 *
 * @author baily
 * @since 2020-07-08
 */
public interface ZsSurveyBenchmarkService extends IService<ZsSurveyBenchmark> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsSurveyBenchmark> selectPage(ZsSurveyBenchmark entity);

    List<ZsSurveyBenchmark> selectListByPids(Integer id);
}
