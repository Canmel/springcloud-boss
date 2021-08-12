package com.camel.realname.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.realname.model.TelProtection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NumberManageMapper extends BaseMapper<TelProtection> {
}
