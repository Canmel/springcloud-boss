package com.camel.survey.service.impl;

import com.camel.core.model.SysUser;
import com.camel.survey.mapper.SysUserMapper;
import com.camel.survey.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getOneByUid(Integer uid) {
        return sysUserMapper.selectByUid(uid);
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer uid) {
        return sysUserMapper.findRoleIdsByUserId(uid);
    }
}
