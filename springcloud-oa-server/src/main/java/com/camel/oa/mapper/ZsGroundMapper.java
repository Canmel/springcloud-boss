package com.camel.oa.mapper;

import com.camel.oa.model.ZsGround;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 地块 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-29
 */
@Repository
public interface ZsGroundMapper extends BaseMapper<ZsGround> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsGround> list(ZsGround entity);
}
