package com.camel.oa.mapper;

import com.camel.oa.model.Questionnaire;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 调查问卷 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-21
 */
@Repository
public interface QuestionnaireMapper extends BaseMapper<Questionnaire> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<Questionnaire> list(Questionnaire entity);
}
