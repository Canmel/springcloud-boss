package com.camel.survey.mapper;

import com.camel.survey.model.Document;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-11
 */
@Repository
public interface DocumentMapper extends BaseMapper<Document> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<Document> list(Document entity);
}
