package com.camel.survey.mapper;

import com.camel.survey.model.ZsReport;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2021-03-17
 */
public interface ZsReportMapper extends BaseMapper<ZsReport> {
    List<ZsReport> list(ZsReport report);
}
