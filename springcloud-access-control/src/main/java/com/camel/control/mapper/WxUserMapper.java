package com.camel.control.mapper;

import com.camel.control.model.WxUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.core.model.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 微信用户信息 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@Mapper
@Repository
public interface WxUserMapper extends BaseMapper<WxUser> {
    List<WxUser> list(SysLog sysLog);
}
