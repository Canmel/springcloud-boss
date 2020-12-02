package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.AreaAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
@Repository
public interface AreaAddressMapper extends BaseMapper<AreaAddress> {
    List<AreaAddress> list(AreaAddress entity);

    List<AreaAddress> selectMatch(@Param("text") String text, @Param("areaId") Integer areaId);

    List<AreaAddress> selectMatchList(@Param("collection") List<String> textList, @Param("areaId") Integer areaId);
}
