package com.camel.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
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

    boolean resetCompany(Integer userid);

    /**
     * 用户分页查询
     * @param user
     * @return
     */
    PageInfo<SysUser> pageQuery(SysUser user);

    SysUser detail(Integer id);

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
     * @return SysUser
     */
    SysUser getRolesByUser(SysUser user);

    /**
     * 给用户添加角色
     * @param user
     * @return
     */
    boolean addRoles(SysUser user);

    /**
     * 所有用户
     * @return
     */
    List<SysUser> all();

    /**
     * 通过角色ID查询用户
     * @param id 角色ID
     * @return
     */
    List<SysUser> byRole(Integer id);

    Result interviewer(SysUser sysUser);

    List<Integer> selectRoleId(SysUser sysUser);

    SysUser selectByUid(Integer id);

    boolean updateSeat(String seatNum,Integer userId);

    /**
     * 将所有seat_num为seatNum的用户坐席号都置为空字符串
     * @param seatNum
     * @return
     */
    boolean emptySeat(String seatNum);

    SysUser selectByIdNum(String idNum);
}
