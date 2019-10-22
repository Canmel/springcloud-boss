package com.camel.oa.mapper;

import com.camel.oa.model.ZsClue;
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
public interface ZsClueMapper extends BaseMapper<ZsClue> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsClue> list(ZsClue entity);
}
