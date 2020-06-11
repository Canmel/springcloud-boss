package com.camel.survey.mapper;

import com.camel.survey.model.ZsCdrinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
@Repository
public interface ZsCdrinfoMapper extends BaseMapper<ZsCdrinfo> {
    ZsCdrinfo selectByAgentUUID(String uuid);

    ZsCdrinfo details(String id);
}
