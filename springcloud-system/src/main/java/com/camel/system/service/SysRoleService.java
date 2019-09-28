package com.camel.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.system.model.SysMenu;
import com.camel.system.model.SysRole;
import com.camel.system.model.SysUser;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 分页查询角色
     * @param sysRole
     * @return
     */
    PageInfo<SysRole> pageQuery(SysRole sysRole);

    /**
     * 通过角色ID查询角色
     * @param ids
     * @return
     */
    List<SysRole> loadRolesByRoleIds(List<Integer> ids);

    /**
     * 角色是否存在
     * @param name
     * @param id
     * @return
     */
    boolean exist(String name, Integer id);

    /**
     * 删除角色
     * @param serializable
     * @return
     */
    boolean delete(Serializable serializable);

    void loadRoleMenus(SysRole role);

    /**
     * 给用户添加角色
     * @param role
     * @return
     */
    boolean addMenus(SysRole role);
}
