package com.camel.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.oa.model.Document;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-10-12
 */
@Mapper
@Repository
public interface DocumentMapper extends BaseMapper<Document> {

    /**
     * 查询文档列表
     * @param entity
     * @return
     */
    List<Document> list(Document entity);
}
