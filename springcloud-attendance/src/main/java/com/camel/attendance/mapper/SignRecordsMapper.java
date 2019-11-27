package com.camel.attendance.mapper;

import com.camel.attendance.model.SignRecords;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 打卡记录 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-11-27
 */
@Repository
public interface SignRecordsMapper extends BaseMapper<SignRecords> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<SignRecords> list(SignRecords entity);
}
