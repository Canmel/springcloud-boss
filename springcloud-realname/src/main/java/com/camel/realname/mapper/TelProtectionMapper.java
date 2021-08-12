package com.camel.realname.mapper;

import com.camel.core.model.SysUser;
import com.camel.realname.model.TelProtection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TelProtectionMapper {

    List<TelProtection> grantTelList(TelProtection telProtection);

    List<SysUser> partnerList(SysUser sysUser, Integer telId);

    Integer updatePartner(TelProtection telProtection);

    Integer isExist(Integer uid, Integer telId);

    Boolean delPromise(TelProtection telProtection);
}
