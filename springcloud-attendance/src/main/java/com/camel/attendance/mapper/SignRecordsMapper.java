package com.camel.attendance.mapper;

import com.camel.attendance.model.SignRecords;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import feign.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

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

    /**
     * 通过月份获取记录
     * @param ydate
     * @param mdate
     * @return
     */
    List<Map<String, String>> selectByMonth(@Param("ydate") String ydate, @Param("mdate") String mdate);
}
