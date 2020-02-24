package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsSeat;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
public interface ZsSeatMapper extends BaseMapper<ZsSeat> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsSeat> list(ZsSeat entity);

    ZsSeat selectLastByUser(Integer id);

}
