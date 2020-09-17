package com.camel.survey.mapper;

import com.camel.survey.model.Args;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-11-22
 */
@Repository
public interface ArgsMapper extends BaseMapper<Args> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<Args> list(Args entity);

    Args selectByCode(String code);
}
