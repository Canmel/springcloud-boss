package com.camel.realname.mapper;

import com.camel.realname.model.ApplyNumber;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2021-07-05
 */
@Mapper
@Repository
public interface ApplyNumberMapper extends BaseMapper<ApplyNumber> {

    List<ApplyNumber> list(ApplyNumber entity);
}
