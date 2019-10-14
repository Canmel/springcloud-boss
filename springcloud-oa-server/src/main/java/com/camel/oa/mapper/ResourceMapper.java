package com.camel.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.oa.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-10-14
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {
    List<Resource> list(Resource resource);
}
