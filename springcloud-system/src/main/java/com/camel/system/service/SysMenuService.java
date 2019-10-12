package com.camel.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.model.SysMenu;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 分页查询
     * @param sysMenu
     * @return
     */
    PageInfo<SysMenu> selectPage(SysMenu sysMenu);

    /**
     * 查询一级菜单
     * @return
     */
    List<SysMenu> tops(Principal principal);

    /**
     * 查询子菜单
     * @return
     */
    List<SysMenu> subs(Principal principal);

    /**
     * 是否存在
     * @param name
     * @param id
     * @return
     */
    boolean exist(String name, Integer id);

    /**
     * 删除
     * @param serializable
     * @return
     */
    boolean delete(Serializable serializable);
}
