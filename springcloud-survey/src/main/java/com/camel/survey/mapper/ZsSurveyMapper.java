package com.camel.survey.mapper;

import com.camel.survey.model.ZsSurvey;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-06
 */
@Repository
public interface ZsSurveyMapper extends BaseMapper<ZsSurvey> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsSurvey> list(ZsSurvey entity);

    /**
     * 查询列表
     * @return
     */
    List<ZsSurvey> all();

    /**
     * 查询文件
     * @param id
     * @return
     */
    ZsSurvey find(Integer id);

    /**
     * 查询问卷简单返回
     * @param id
     * @return
     */
    ZsSurvey findById(Integer id);

    void updateCurrent(Integer id);

    ZsSurvey findLatestSurvey();

    Integer avgTime(Integer id);
}
