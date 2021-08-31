package com.camel.survey.service;

import com.camel.core.model.SysUser;

import java.util.List;

public interface SysUserService {
    /**
     * 根据用户uid获取用户信息
     * @param uid 用户uid
     * @return 用户信息
     */
    SysUser getOneByUid(Integer uid);

    /**
     * 根据用户uid获取角色id列表
     * @param uid 用户uid
     * @return 角色id列表
     */
    List<Integer> getRoleIdsByUserId(Integer uid);
}
