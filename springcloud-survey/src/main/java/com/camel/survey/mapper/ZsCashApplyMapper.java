package com.camel.survey.mapper;

import com.camel.survey.model.ZsCashApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsConcat;

import java.util.List;

/**
 * <p>
 * 提现申请 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-03-21
 */
public interface ZsCashApplyMapper extends BaseMapper<ZsCashApply> {
    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    List<ZsCashApply> list(ZsCashApply entity);
}
