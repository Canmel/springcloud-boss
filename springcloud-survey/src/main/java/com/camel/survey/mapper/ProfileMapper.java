package com.camel.survey.mapper;

import com.camel.survey.vo.ZsHomeTotal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-11-22
 */
@Repository
public interface ProfileMapper {

    /**
     * 首页统计
     * @return
     */
    Map<String, Object> total(Integer uid);
}
