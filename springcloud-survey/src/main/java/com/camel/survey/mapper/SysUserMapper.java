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
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectByUid(Integer uid);

    List<Integer> findRoleIdsByUserId(Integer uid);
}
