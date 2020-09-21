package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsAnswerItem;
import feign.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-17
 */
@Repository
public interface ZsAnswerItemMapper extends BaseMapper<ZsAnswerItem> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsAnswerItem> list(ZsAnswerItem entity);

    Map<String, Object> selectCrossCount(@Param("qF") String qF, @Param("qS") String qS, @Param("oF") String oF, @Param("oS") String oS, @Param("surveyId") Integer surveyId);

    List<Map<String, Object>> selectCrossCounts(@Param("qF") Integer qF, @Param("qS") Integer qS, @Param("oS") Integer oS, @Param("surveyId") Integer surveyId);

    List<ZsAnswerItem> selectByAnswerId(Integer id);

    List<ZsAnswerItem> selectBySurvey(Integer id);

    List<ZsAnswerItem> selectBySurveyLimit(Integer id, Integer limit);

    List<Map<String, Object>> selectExport(Integer id);

    List<Map<String, Object>> selectWorkNumTotal(Integer id);

    int chageInvalidByAnswer(Integer id, Integer valid);

    Integer selectAnswerItemCount(Integer surveyId, Integer qId, Integer oId);
}
