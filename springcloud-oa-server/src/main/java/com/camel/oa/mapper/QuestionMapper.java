package com.camel.oa.mapper;

import com.camel.oa.model.Question;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-21
 */
@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<Question> list(Question entity);
}
