package com.camel.survey.mapper;

import com.camel.survey.model.ZsWorkShift;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-04-14
 */
@Repository
public interface ZsWorkShiftMapper extends BaseMapper<ZsWorkShift> {

    /**
     * 查询列表
     * @param
     * @return
     */
    List<ZsWorkShift> list(ZsWorkShift entity);
}
