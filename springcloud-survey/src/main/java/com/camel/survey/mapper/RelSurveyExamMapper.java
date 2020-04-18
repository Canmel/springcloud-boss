package com.camel.survey.mapper;

import com.camel.survey.model.RelSurveyExam;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-13
 */
@Repository
public interface RelSurveyExamMapper extends BaseMapper<RelSurveyExam> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<RelSurveyExam> list(RelSurveyExam entity);
}
