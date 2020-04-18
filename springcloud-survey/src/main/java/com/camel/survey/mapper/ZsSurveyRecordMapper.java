package com.camel.survey.mapper;

import com.camel.survey.model.ZsSurveyRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 问卷下发或电话访问等发出记录 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-18
 */
@Repository
public interface ZsSurveyRecordMapper extends BaseMapper<ZsSurveyRecord> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsSurveyRecord> list(ZsSurveyRecord entity);
}
