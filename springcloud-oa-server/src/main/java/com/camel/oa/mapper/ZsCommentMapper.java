package com.camel.oa.mapper;

import com.camel.oa.model.ZsComment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-23
 */
@Repository
public interface ZsCommentMapper extends BaseMapper<ZsComment> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsComment> list(ZsComment entity);
}
