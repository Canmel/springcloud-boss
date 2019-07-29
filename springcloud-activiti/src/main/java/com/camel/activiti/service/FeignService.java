package com.camel.activiti.service;

import com.camel.core.entity.Result;

public interface FeignService {
    /**
     * 查询所有角色
     * @return
     */
    Result allRole();

    /**
     * 查询指定角色的用户
     * @return
     */
    Result usersByRole(Integer id);
}
