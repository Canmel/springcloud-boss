package com.camel.survey.mapper;

import com.camel.survey.model.ZsExam;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-12
 */
@Repository
public interface ZsExamMapper extends BaseMapper<ZsExam> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsExam> list(ZsExam entity);

    List<ZsExam> listBySurveyId(Integer id);

    List<ZsExam> listByUserId(Integer id);
}
