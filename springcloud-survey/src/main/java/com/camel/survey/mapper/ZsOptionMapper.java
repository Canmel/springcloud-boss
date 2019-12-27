package com.camel.survey.mapper;

import com.camel.survey.model.ZsOption;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import feign.Param;
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
public interface ZsOptionMapper extends BaseMapper<ZsOption> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsOption> list(ZsOption entity);

    /**
     * 通过问题ID获取选项
     * @param questionId
     * @return
     */
    List<ZsOption> selectByQuestionId(@Param("questionId") Integer questionId);

    List<ZsOption> selectFullByQuestionId(@Param("questionId") Integer questionId);

    List<ZsOption> selectBySurveyId(Integer id);

    ZsOption selectByQuestionAndName(@Param("questionId") Integer questionId, @Param("name") String name);
}
