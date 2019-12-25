package com.camel.survey.mapper;

import com.camel.survey.model.ZsAnswer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface ZsAnswerMapper extends BaseMapper<ZsAnswer> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsAnswer> list(ZsAnswer entity);

    List<Map<String, Object>> selectRateBySurveyQuestion(@Param("id") Integer id, @Param("question") String question);
}
