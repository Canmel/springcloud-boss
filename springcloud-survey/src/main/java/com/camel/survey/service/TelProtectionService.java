package com.camel.survey.service;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.survey.model.TelProtection;
import com.camel.survey.vo.CompanyVo;
import com.camel.survey.vo.FinalCusVo;
import com.camel.survey.vo.NumberVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TelProtectionService extends IService<TelProtection> {

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

    PageInfo<CompanyVo> partnerList(SysCompany sysCompany, Integer telId);

    Integer isExist(Integer uid, Integer telId);

    Result grant(TelProtection telProtection);

    Result revoke(TelProtection telProtection);

    PageInfo<JSONArray> all(NumberVo numberVo);

    PageInfo<FinalCusVo> finalList(SysUser sysUser,String tel);

    Integer revokeTel(Integer id);

    Result grantFinal(TelProtection telProtection);

    /**
     * 根据最终用户id和项目id查询对应的集合
     * @param projectId
     * @return
     */
    List<String> getTelListByProId(Integer projectId);

    Result getFinalName(String tel);

}
