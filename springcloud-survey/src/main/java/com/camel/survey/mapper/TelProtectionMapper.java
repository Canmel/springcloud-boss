package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.survey.model.TelProtection;
import com.camel.survey.vo.FinalCusVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TelProtectionMapper extends BaseMapper<TelProtection> {

    List<TelProtection> grantTelList(TelProtection telProtection);

    List<SysCompany> partnerList(@Param("sysCompany") SysCompany sysCompany, @Param("telId") Integer telId);

    Integer updatePartner(TelProtection telProtection);

    Integer isExist(Integer uid, Integer telId);

    Integer delPromise(TelProtection telProtection);

    /**
     * 查询角色为最终用户的用户
     * @param sysUser
     * @return FinalCusVo 对象
     */
    List<FinalCusVo> finalList(SysUser sysUser);

    /**
     * 解绑
     */
    Integer revokeTel(Integer id);

    Integer insertFinal(TelProtection telProtection);

    /**
     * 根据项目id返回电话集合
     * @param projectId 项目id
     * @return List<String>
     */
    List<String> getTelByProId(@Param("projectId")Integer projectId);

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
     * @param projectId, id 修改条件
     * @return boolean
     */
    int updateByTid(Integer projectId, Integer id);

    List<String> selectByTel(String tel);
}
