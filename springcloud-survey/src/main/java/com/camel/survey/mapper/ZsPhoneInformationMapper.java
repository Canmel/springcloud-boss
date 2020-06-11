package com.camel.survey.mapper;

import com.camel.survey.model.ZsPhoneInformation;
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
 * @since 2020-06-10
 */
@Repository
public interface ZsPhoneInformationMapper extends BaseMapper<ZsPhoneInformation> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsPhoneInformation> list(ZsPhoneInformation entity);


}
