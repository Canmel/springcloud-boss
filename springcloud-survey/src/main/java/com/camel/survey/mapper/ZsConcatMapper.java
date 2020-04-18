package com.camel.survey.mapper;

import com.camel.survey.model.ZsConcat;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-16
 */
@Repository
public interface ZsConcatMapper extends BaseMapper<ZsConcat> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsConcat> list(ZsConcat entity);
}
