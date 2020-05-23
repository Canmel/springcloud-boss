package com.camel.survey.mapper;

import com.camel.survey.model.ZsSign;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 问卷调查报名记录 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-13
 */
@Repository
public interface ZsSignMapper extends BaseMapper<ZsSign> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsSign> list(ZsSign entity);

    List<ZsSign> allInSurvey(Integer id);

    ZsSign selectLastByUser(Integer id);

}
