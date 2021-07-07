package com.camel.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.camel.auth.dao.SysUserDao;
import com.camel.auth.model.MyUserDetails;
import com.camel.auth.model.SysMenu;
import com.camel.auth.model.SysRole;
import com.camel.auth.model.SysUser;
import com.camel.common.entity.Member;
import com.camel.common.utils.SerizlizeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
 * 自定义用户详情服务
 * @author baily
 * @since 1.0
 * @date 2019/7/4
 **/
@Service("userDetailService")
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserDao.findByUserName(username);
        if(ObjectUtil.isEmpty(sysUser)) {
            return null;
        }
        Member member = new Member(sysUser.getUid(), sysUser.getUsername());
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        byte[] su = SerizlizeUtil.serialize(member);
        operations.set(sysUser.getUsername(), su);
        if (sysUser == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // 可用性 :true:可用 false:不可用
        boolean enabled = true;
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = sysUser.getLoginFailureCount() < 3;
        for (SysRole role : sysUser.getSysRoles()) {
            //角色必须是ROLE_开头，可以在数据库中设置
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase());
            grantedAuthorities.add(grantedAuthority);
            //获取权限
            for (SysMenu menu : role.getSysMenus()) {
                if(!StringUtils.isBlank(menu.getUrl())){
                    GrantedAuthority authority = new SimpleGrantedAuthority(menu.getUrl());
                    grantedAuthorities.add(authority);
                }
            }
        }
        MyUserDetails user = new MyUserDetails(sysUser, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        return user;
    }

}

