package com.camel.survey.mapper;

import com.camel.survey.model.ZsDelivery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 考核投递记录 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-12
 */
@Repository
public interface ZsDeliveryMapper extends BaseMapper<ZsDelivery> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsDelivery> list(ZsDelivery entity);
}
