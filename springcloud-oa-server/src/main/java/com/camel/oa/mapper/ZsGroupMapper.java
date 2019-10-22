package com.camel.oa.mapper;

import com.camel.oa.model.ZsGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 项目组 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-22
 */
@Repository
public interface ZsGroupMapper extends BaseMapper<ZsGroup> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsGroup> list(ZsGroup entity);
}
