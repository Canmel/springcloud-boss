package com.camel.survey.mapper;

import com.camel.survey.model.ZsOtherSurvey;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsSurvey;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 其他平台问卷 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-06-18
 */
@Repository
public interface ZsOtherSurveyMapper extends BaseMapper<ZsOtherSurvey> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsOtherSurvey> list(ZsOtherSurvey entity);

    List<ZsOtherSurvey> appointments(Integer uid);

}
