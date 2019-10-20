package com.camel.oa.mapper;

import com.camel.oa.model.Project;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-20
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<Project> list(Project entity);
}
