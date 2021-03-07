package com.camel.survey.mapper;

import com.camel.survey.model.ZsCallPlan;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2021-02-01
 */
@Mapper
@Repository
public interface ZsCallPlanMapper extends BaseMapper<ZsCallPlan> {

    List<ZsCallPlan> list(ZsCallPlan entity);
}
