package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.BaseArea;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 统一地区库 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@Repository
public interface BaseAreaMapper extends BaseMapper<BaseArea> {
    List<BaseArea> list(BaseArea entity);
}
