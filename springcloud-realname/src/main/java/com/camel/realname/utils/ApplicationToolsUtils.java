package com.camel.realname.utils;

import com.camel.core.model.SysUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * ___====-_  _-====___
 * _--^^^#####//      \\#####^^^--_
 * _-^##########// (    ) \\##########^-_
 * -############//  |\^^/|  \\############-
 * _/############//   (@::@)   \\############\_
 * /#############((     \\//     ))#############\
 * -###############\\    (oo)    //###############-
 * -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 * _#/|##########/\######(   /\   )######/\##########|\#_
 * |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 * `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 * `   `  `      `   / | |  | | \   '      '  '   '
 * (  | |  | |  )
 * __\ | |  | | /__
 * (vvv(VVV)(VVV)vvv)
 * <系统级工具>
 *
 * @author baily
 * @date 2019/10/31
 * @since 1.0
 **/
@Component
public class ApplicationToolsUtils {
    private final static ApplicationToolsUtils INSTANCE = new ApplicationToolsUtils();

    public static SysUser result = new SysUser();

    public ApplicationToolsUtils() {
    }

    public static ApplicationToolsUtils getInstance() {
        return INSTANCE;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    public SysUser currentUser(OAuth2Authentication oAuth2Authentication) {
        ObjectMapper mapper = new ObjectMapper();
        SysUser user = null;
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
        LinkedHashMap tokenDetails = (LinkedHashMap) token.getDetails();
        LinkedHashMap p = (LinkedHashMap) tokenDetails.get("principal");
        LinkedHashMap userInfo = (LinkedHashMap) p.get("sysUser");
        if (!CollectionUtils.isEmpty(userInfo)) {
            user = mapper.convertValue(userInfo, SysUser.class);
            System.out.println(user);
        }
        return user;
    }

    public SysUser currentUser() {
        try {
            return currentUser((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean hasRole(String roleName) {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> auths = authentication.getAuthorities();
        for (GrantedAuthority authority : auths) {
            if (authority.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
