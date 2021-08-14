package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.survey.model.TelProtection;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TelProtectionService {

    /**
     * 供应商：分页查询号码列表
     * @param telProtection 查询条件
     * @return Result
     */
    PageInfo<TelProtection> queryByPid(TelProtection telProtection);

    /**
     * 供应商：分页查询项目列表
     * @param telProtection 查询条件
     * @return Result
     */
    PageInfo<TelProtection> queryByFid(TelProtection telProtection);

    /**
     * 供应商：修改项目
     * @param surveyId, id 修改条件
     * @return boolean
     */
    boolean modifiByTid(Integer surveyId, Integer id);

    PageInfo<TelProtection> grantTelList(TelProtection telProtection);

    PageInfo<SysUser> partnerList(SysUser sysUser,Integer telId);

    Integer isExist(Integer uid, Integer telId);

    Result grant(TelProtection telProtection);

    Result revoke(TelProtection telProtection);

    /**
     * 根据最终用户id和项目id查询对应的集合
     * @param projectId
     * @return
     */
    List<String> getTelListByUserId(Integer projectId);

}
