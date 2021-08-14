package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.survey.model.TelProtection;
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

    Boolean delPromise(TelProtection telProtection);

    List<SysCompany> finalList(SysCompany sysCompany);

    Integer insertFinal(TelProtection telProtection);

    /**
     * 根据最终客户id返回电话集合
     * @param userId 用户id
     * @param projectId 项目id
     * @return List<String>
     */
    List<String> getTelByUserId(@Param("userId") Integer userId, @Param("projectId")Integer projectId);

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
