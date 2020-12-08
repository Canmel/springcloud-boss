package com.camel.survey.mapper;

import com.camel.core.model.ZsAgency;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-12-08
 */
@Repository
public interface ZsAgencyMapper extends BaseMapper<ZsAgency> {
    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsAgency> list(ZsAgency entity);
}
