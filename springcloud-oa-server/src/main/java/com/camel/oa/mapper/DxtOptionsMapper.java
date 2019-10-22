package com.camel.oa.mapper;

import com.camel.oa.model.DxtOptions;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-22
 */
@Repository
public interface DxtOptionsMapper extends BaseMapper<DxtOptions> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<DxtOptions> list(DxtOptions entity);
}
