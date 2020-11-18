package com.camel.survey.mapper;

import com.camel.survey.model.Args;
import com.camel.survey.model.SysSeat;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 坐席表 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-05-30
 */
@Repository
public interface SysSeatMapper extends BaseMapper<SysSeat> {
    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<SysSeat> list(SysSeat entity);
}
