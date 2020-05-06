package com.camel.survey.mapper;

import com.camel.survey.model.ZsCdrinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
public interface ZsCdrinfoMapper extends BaseMapper<ZsCdrinfo> {
    ZsCdrinfo selectByAgentUUID(String uuid);
}
