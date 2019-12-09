package com.camel.survey.mapper;

import com.camel.survey.model.ZsQuestion;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-09
 */
@Repository
public interface ZsQuestionMapper extends BaseMapper<ZsQuestion> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsQuestion> list(ZsQuestion entity);
}
