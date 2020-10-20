package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.HangUp;
import com.camel.survey.model.SysSeat;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-10-20
 */
public interface HangUpMapper extends BaseMapper<HangUp> {

    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    List<HangUp> list(HangUp entity);
}
