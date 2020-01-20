package com.camel.survey.utils;

import com.camel.common.entity.Member;
import com.camel.core.model.SysUser;
import com.camel.redis.utils.SerizlizeUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.model.ZsSurveyBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
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

    public static SysUser result = new SysUser();

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
        if(CollectionUtils.isEmpty(userList)) {
            return userList;
        }
        return new ArrayList<SysUser>();
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

    public void setCurrentUser(ZsSurveyBaseEntity entity, OAuth2Authentication oAuth2Authentication){
        Member member = currentUser(oAuth2Authentication);
        entity.setCreatorId(member.getId());
        entity.setCreator(new SysUser(member.getId(), member.getMemberName()));
    }

    public Member currentUser() {
        return currentUser((OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication());
    }
}
