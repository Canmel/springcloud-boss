package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.SysSeat;
import com.camel.survey.model.ZsAgencyFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-09-28
 */
@Repository
public interface ZsAgencyFeeMapper extends BaseMapper<ZsAgencyFee> {
    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    List<ZsAgencyFee> list(ZsAgencyFee entity);
}
