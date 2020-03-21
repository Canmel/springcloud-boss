package com.camel.control.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.control.model.DRecord;
import com.camel.core.model.SysLog;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
public interface DRecordMapper extends BaseMapper<DRecord> {
    List<DRecord> list(SysLog sysLog);
}
