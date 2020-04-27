package com.camel.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.model.SysRole;
import com.camel.core.utils.MyCollectionUtils;
import com.camel.core.utils.ResultUtil;
import com.camel.system.mapper.SysRoleMapper;
import com.camel.system.mapper.SysUserMapper;
import com.camel.system.mapper.SysUserRoleMapper;
import com.camel.core.model.SysUser;
import com.camel.core.model.SysUserRole;
import com.camel.system.service.SysRoleService;
import com.camel.system.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper mapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysUser> pageQuery(SysUser entity) {
        PageInfo pageInfo = PageHelper.startPage(entity.getPageNum(), entity.getPageSize()).doSelectPageInfo(()-> mapper.list(entity));
        return pageInfo;
    }

    @Override
    public boolean exist(String name, Integer id) {
        Wrapper<SysUser> userWrapper = new EntityWrapper<>();
        userWrapper.eq("username", name);
        if(!ObjectUtils.isEmpty(id)){
            userWrapper.notIn("uid", id);
        }

        Integer count = mapper.selectCount(userWrapper);
        return count <= 0;
    }

    @Override
    public SysUser getRolesByUser(SysUser user) {
        Wrapper<SysUserRole> sysUserRoleWrapper = new EntityWrapper<>();
        sysUserRoleWrapper.eq("user_id", user.getUid());
        List<SysUserRole> userRoleList = userRoleMapper.selectList(sysUserRoleWrapper);
        List<Integer> roleIds = new ArrayList<>();
        userRoleList.forEach(userRole -> {
            roleIds.add(userRole.getRoleId());
        });
        user.setRoleIds(roleIds);
        user.setSysRoles(roleService.loadRolesByRoleIds(roleIds));
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    public boolean addRoles(SysUser user) {
        Wrapper<SysUserRole> userRoleWrapper = new EntityWrapper<>();
        userRoleWrapper.eq("user_id", user.getUid());

        userRoleMapper.delete(userRoleWrapper);
        MyCollectionUtils.removeRepeat(user.getRoleIds()).forEach(roleId -> {
            userRoleMapper.insert(new SysUserRole(user.getUid(), (Integer) roleId));
        });
        return true;
    }

    @Override
    public List<SysUser> all() {
        Wrapper<SysUser> userWrapper = new EntityWrapper<>();
        // TODO 去除密码项
        return mapper.selectList(userWrapper);
    }

    @Override
    public List<SysUser> byRole(Integer id) {
        return mapper.byRole(id);
    }

    @Override
    public Result interviewer(SysUser sysUser) {
        Integer id = mapper.insert(sysUser);
        SysRole role = sysRoleMapper.selectOne(SysRole.interviewer());
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(id);
        userRole.setRoleId(role.getRoleId());
        userRoleMapper.insert(userRole);
        return ResultUtil.success("新增访员成功");
    }

    @Override
    public SysUser selectByUid(Integer id) {
        Wrapper<SysUser> sysUserWrapper = new EntityWrapper<>();
        sysUserWrapper.eq("uid", id);
        return selectOne(sysUserWrapper);
    }
}
