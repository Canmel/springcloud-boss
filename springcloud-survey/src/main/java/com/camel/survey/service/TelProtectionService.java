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

    /**
     * 显示最终用户所有电话
     * @param telProtection
     * @return
     */
    PageInfo<TelProtection> grantTelList(TelProtection telProtection);

    /**
     * 显示所有合作伙伴
     * @param sysCompany
     * @param telId
     * @return
     */
    PageInfo<CompanyVo> partnerList(SysCompany sysCompany, Integer telId);

    /**
     * 判断合作伙伴是否绑定了电话
     * @param uid
     * @param telId
     * @return
     */
    Integer isExist(Integer uid, Integer telId);

    /**
     * 对供应商进行授权
     * @param telProtection
     * @return
     */
    Result grant(TelProtection telProtection);

    /**
     * 撤销授权
     * @param telProtection
     * @return
     */
    Result revoke(TelProtection telProtection);

    /**
     * 从cti获取所有接入号
     * @param numberVo
     * @return
     */
    PageInfo<JSONArray> all(NumberVo numberVo);

    /**
     * 最终用户列表
     * @param sysUser
     * @param tel
     * @return
     */
    PageInfo<FinalCusVo> finalList(SysUser sysUser,String tel);

    /**
     * 解除号码和最终用户的绑定
     * @param id
     * @return
     */
    Integer revokeTel(Integer id);

    /**
     * 号码绑定最终用户
     * @param telProtection
     * @return
     */
    Result grantFinal(TelProtection telProtection);

    /**
     * 根据最终用户id和项目id查询对应的集合
     * @param projectId
     * @return
     */
    List<String> getTelListByProId(Integer projectId);

    /**
     * 获得号码所属最终用户名字
     * @param tel
     * @return
     */
    Result getFinalName(String tel);

    List<TelProtection> getPartnerName(String tel);
}
