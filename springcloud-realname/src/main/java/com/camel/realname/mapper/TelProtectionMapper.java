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

    /**
     * 供应商：分页查询号码列表
     * @param telProtection 查询条件
     * @return Result
     */
    List<TelProtection> selectByPid(TelProtection telProtection);

    /**
     * 供应商：分页查询项目列表
     * @param telProtection 查询条件
     * @return Result
     */
    List<TelProtection> selectByFid(TelProtection telProtection);

    /**
     * 供应商：修改项目
     * @param surveyId, id 修改条件
     * @return boolean
     */
    int updateByTid(Integer surveyId, Integer id);
}
