package com.camel.survey.mapper;

import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsUnionConfigration;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2021-05-05
 */
public interface ZsUnionConfigrationMapper extends BaseMapper<ZsUnionConfigration> {

    List<ZsUnionConfigration> list(ZsUnionConfigration configration);

    int selectValidAnserCountByMultipleOption(@Param("options") List<Integer> options, @Param("surveyId") Integer surveyId);
}
