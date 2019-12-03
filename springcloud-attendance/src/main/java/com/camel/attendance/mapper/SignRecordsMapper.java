package com.camel.attendance.mapper;

import com.camel.attendance.model.SignRecords;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.attendance.vo.SignRecordTotal;
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
    List<Map<String, Object>> selectByMonth(@Param("ydate") String ydate, @Param("mdate") String mdate);

    /**
     * 通过时间和人员选择所有打卡记录
     * @param signRecords
     * @return
     */
    List<SignRecords> selectByDay(SignRecords signRecords);

    /**
     *
     * @param signRecords
     * @return
     */
    List<SignRecords> selectTotalByMonth(SignRecords signRecords);

    /**
     * 查询最后一个有效的考勤
     * @param signRecords
     * @return
     */
    SignRecords selectLastValidRecord(SignRecords signRecords);
}
