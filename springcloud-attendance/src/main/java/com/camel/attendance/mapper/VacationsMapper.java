package com.camel.attendance.mapper;

import com.camel.attendance.model.Vacations;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 假期表 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-11-27
 */
@Repository
public interface VacationsMapper extends BaseMapper<Vacations> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<Vacations> list(Vacations entity);
}
