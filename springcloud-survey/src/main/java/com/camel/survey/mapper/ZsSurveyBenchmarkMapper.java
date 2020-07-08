package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsSurveyBenchmark;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 项目基准 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-07-08
 */
@Repository
public interface ZsSurveyBenchmarkMapper extends BaseMapper<ZsSurveyBenchmark> {
    List<ZsSurveyBenchmark> list(ZsSurveyBenchmark entity);
}
