package com.camel.survey.utils;

import com.camel.common.entity.Member;
import com.camel.core.model.SysUser;
import com.camel.redis.utils.SerizlizeUtil;
import com.camel.redis.utils.SessionContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

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
 * <系统级工具>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@Component
public class ApplicationToolsUtils {
    private final static ApplicationToolsUtils INSTANCE = new ApplicationToolsUtils();

    public static SysUser result = null;

    public ApplicationToolsUtils() {
    }

    public static ApplicationToolsUtils getInstance() {
        return INSTANCE;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    public List<SysUser> allUsers() {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        byte[] cu = (byte[]) operations.get("ALL_SYS_USERS");
        List<SysUser> userList = (List<SysUser>) SerizlizeUtil.unserizlize(cu);
        return userList;
    }

    public SysUser getUser(Integer uid) {
        allUsers().forEach(user -> {
            if (user.getUid().equals(uid)) {
                result = user;
                return;
            }
        });
        return result;
    }

    public Member currentUser(OAuth2Authentication oAuth2Authentication) {
        return (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
    }
}
