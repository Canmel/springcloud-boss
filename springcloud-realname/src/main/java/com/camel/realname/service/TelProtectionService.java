package com.camel.realname.service;

import cn.hutool.json.JSONArray;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.realname.model.TelProtection;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

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
     * @param projectId, id 修改条件
     * @return boolean
     */
    boolean modifiByTid(Integer projectId, Integer id);

    PageInfo<TelProtection> grantTelList(TelProtection telProtection);

    PageInfo<SysUser> partnerList(@Param("sysUser") SysUser sysUser,@Param("telId") Integer telId);

    Integer isExist(Integer uid, Integer telId);

    Result grant(TelProtection telProtection);

    Result revoke(TelProtection telProtection);

    JSONArray all();

    List<SysUser> finalList();

}
