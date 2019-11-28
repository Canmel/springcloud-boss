package com.camel.attendance.mapper;

import com.camel.attendance.model.VacationDays;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-11-27
 */
@Repository
public interface VacationDaysMapper extends BaseMapper<VacationDays> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<VacationDays> list(VacationDays entity);
}
