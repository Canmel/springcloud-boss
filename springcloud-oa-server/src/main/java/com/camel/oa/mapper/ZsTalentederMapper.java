package com.camel.oa.mapper;

import com.camel.oa.model.ZsTalenteder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-30
 */
@Repository
public interface ZsTalentederMapper extends BaseMapper<ZsTalenteder> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsTalenteder> list(ZsTalenteder entity);
}
