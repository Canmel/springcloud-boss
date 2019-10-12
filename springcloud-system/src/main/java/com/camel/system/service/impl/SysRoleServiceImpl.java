package com.camel.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.MyCollectionUtils;
import com.camel.core.enums.RoleStatus;
import com.camel.system.mapper.SysRoleMapper;
import com.camel.system.mapper.SysRoleMenuMapper;
import com.camel.core.model.SysRole;
import com.camel.core.model.SysRoleMenu;
import com.camel.system.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 <p>
 服务实现类
 </p>
 @author ${author}
 @since 2019-04-19 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper mapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public PageInfo<SysRole> pageQuery(SysRole entity) {
        entity.setStatus(RoleStatus.NORMAL.getCode());
        PageInfo pageInfo = PageHelper.startPage(entity.getPageNum(), entity.getPageSize()).doSelectPageInfo(() -> mapper.list(entity));
        return pageInfo;
    }

    @Override
    public List<SysRole> loadRolesByRoleIds(List<Integer> ids) {
        Wrapper<SysRole> roleWrapper = new EntityWrapper<>();
        roleWrapper.in("role_id", ids);
        return mapper.selectList(roleWrapper);
    }

    @Override
    public boolean exist(String name, Integer id) {
        Wrapper<SysRole> roleWrapper = new EntityWrapper<>();
        roleWrapper.eq("role_name", name);
        if (!ObjectUtils.isEmpty(id)) {
            roleWrapper.notIn("role_id", id);
        }
        Integer count = mapper.selectCount(roleWrapper);
        return count <= 0;
    }

    @Override
    public boolean delete(Serializable serializable) {
        SysRole sysRole = new SysRole((Integer) serializable, RoleStatus.UNVALID.getCode());
        return mapper.updateById(sysRole) > -1;
    }

    @Override
    public void loadRoleMenus(SysRole role) {
        Wrapper<SysRoleMenu> sysRoleMenuWrapper = new EntityWrapper<>();
        sysRoleMenuWrapper.eq("role_id", role.getRoleId());
        List<SysRoleMenu> sysRoleMenuList = roleMenuMapper.selectList(sysRoleMenuWrapper);
        List<Integer> menuIds = new ArrayList<>();
        sysRoleMenuList.forEach(roleMenu -> {
            menuIds.add(roleMenu.getMenuId());
        });
        role.setMenuIds(menuIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    public boolean addMenus(SysRole role) {
        Wrapper<SysRoleMenu> roleMenuWrapper = new EntityWrapper<>();
        roleMenuWrapper.eq("role_id", role.getRoleId());

        roleMenuMapper.delete(roleMenuWrapper);
        MyCollectionUtils.removeRepeat(role.getMenuIds()).forEach(menuId -> {
            roleMenuMapper.insert(new SysRoleMenu(role.getRoleId(), (Integer) menuId));
        });
        return true;
    }

}
