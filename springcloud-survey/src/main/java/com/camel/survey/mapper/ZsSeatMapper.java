package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsSeat;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
@Repository
public interface ZsSeatMapper extends BaseMapper<ZsSeat> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsSeat> list(ZsSeat entity);

    ZsSeat selectLastByUser(Integer id);

    ZsSeat searchBySeatNum(String seatNum);

    /**
     * 回收坐席，将该坐席的uid清空，state改为0
     * @param id
     * @return
     */
    int callback(Integer id);

    /**
     * 分配坐席，修改dw_oauth2表，将对应用户的seat_num置为seatNum
     * @param seatNum
     * @param uid
     * @return
     */
    int assignSeat(String seatNum,Integer uid);

}
