package com.camel.oa.mapper;

import com.camel.oa.model.ZsMerchant;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-10-30
 */
@Repository
public interface ZsMerchantMapper extends BaseMapper<ZsMerchant> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsMerchant> list(ZsMerchant entity);
}
