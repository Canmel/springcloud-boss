package com.camel.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.model.SysRole;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <角色服务>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
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

    /**
     * 获取角色的菜单
     * @param role
     */
    void loadRoleMenus(SysRole role);

    /**
     * 给用户添加角色
     * @param role
     * @return
     */
    boolean addMenus(SysRole role);
}
