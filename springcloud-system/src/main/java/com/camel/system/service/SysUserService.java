package com.camel.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.system.model.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-22
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 用户分页查询
     * @param user
     * @return
     */
    PageInfo<SysUser> pageQuery(SysUser user);

    /**
     * 判断用户是否存在
     * @param name
     * @param id
     * @return
     */
    boolean exist(String name, Integer id);

    /**
     * 获取用户的所有角色
     * @param user
     */
    void getRolesByUser(SysUser user);

    /**
     * 给用户添加角色
     * @param user
     * @return
     */
    boolean addRoles(SysUser user);

    /**
     * 所有用户
     * @param user
     * @return
     */
    List<SysUser> all(SysUser user);

    /**
     * 通过角色ID查询用户
     * @param id 角色ID
     * @return
     */
    List<SysUser> byRole(Integer id);
}
