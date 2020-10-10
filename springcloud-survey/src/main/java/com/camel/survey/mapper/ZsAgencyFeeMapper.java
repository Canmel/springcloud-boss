package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.SysSeat;
import com.camel.survey.model.ZsAgencyFee;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-09-28
 */
public interface ZsAgencyFeeMapper extends BaseMapper<ZsAgencyFee> {
    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    List<SysSeat> list(ZsAgencyFee entity);
}
