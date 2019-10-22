package com.camel.oa.mapper;

import com.camel.oa.model.ZsProject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 智慧招商项目 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-22
 */
@Repository
public interface ZsProjectMapper extends BaseMapper<ZsProject> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsProject> list(ZsProject entity);
}
