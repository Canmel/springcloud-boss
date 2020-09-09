package com.camel.survey.mapper;

import com.camel.survey.model.ZsAnswer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import feign.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-17
 */
@Repository
public interface ZsAnswerMapper extends BaseMapper<ZsAnswer> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsAnswer> list(ZsAnswer entity);

    Set<String> selectAgentUuidsByEntity(ZsAnswer entity);

    List<Map<String, Object>> selectRateBySurveyQuestion(@Param("id") Integer id, @Param("question") String question);

    Map<String, Object> selectCountCross(@Param("question") String question, @Param("option") String option, @Param("mainQuestion") String mainQuestion, @Param("mianOption") String mianOption, @Param("surveyId") Integer surveyId);

    List<ZsAnswer> selectBySurvey(Integer surveyIds);

    ZsAnswer details(Integer id);

    ZsAnswer full(Integer id);

    ZsAnswer detailsA(String agent);

    List<ZsAnswer> selectDetailsBySurveyId(Integer id, Integer rowNum);

    List<ZsAnswer> selectAllWithConversation(Integer id);

    List<ZsAnswer> selectByAgentUuid(String agentUuid);

    List<ZsAnswer> randomList(Integer surveyId);

    String selectTimeRange(Integer surveyId);
}
