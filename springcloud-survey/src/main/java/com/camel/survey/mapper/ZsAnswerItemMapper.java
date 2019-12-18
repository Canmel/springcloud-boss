package com.camel.survey.mapper;

import com.camel.survey.model.ZsAnswerItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-17
 */
@Repository
public interface ZsAnswerItemMapper extends BaseMapper<ZsAnswerItem> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsAnswerItem> list(ZsAnswerItem entity);
}
