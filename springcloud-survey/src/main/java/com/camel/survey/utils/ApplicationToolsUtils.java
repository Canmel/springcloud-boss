package com.camel.survey.utils;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.survey.feign.SpringCloudSystemFeignClient;
import com.camel.survey.model.ZsSurveyBaseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private SpringCloudSystemFeignClient springCloudSystemFeignClient;

    public List<SysUser> allUsers() {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        List<SysUser> userList = new ArrayList<>();
        List<SysUser> r = new ArrayList<>();
        byte[] cu = (byte[]) operations.get("ALL_SYS_USERS");
        if(ObjectUtils.isEmpty(cu)) {
            Result result = springCloudSystemFeignClient.allUser();
            if(!ObjectUtils.isEmpty(result)) {
                userList = (List<SysUser>) result.getData();
                ObjectMapper mapper = new ObjectMapper();
                for (Object user: userList) {
                    SysUser sysUser = mapper.convertValue(user, SysUser.class);
                    if (user.getClass().equals(LinkedHashMap.class)) {
                        r.add(sysUser);
                    }
                }
                byte[] su = com.camel.common.utils.SerizlizeUtil.serialize(r);
                operations.set("ALL_SYS_USERS", su, 9999, TimeUnit.DAYS);
            }
        } else {
            r = (List<SysUser>) com.camel.common.utils.SerizlizeUtil.unserizlize(cu);
        }
        return r;
    }

    public SysUser getUser(Integer uid) {
        result = null;
        allUsers().forEach(user -> {
            if (user.getUid().equals(uid)) {
                result = user;
                return;
            }
        });
        return result;
    }

    public SysUser currentUser(OAuth2Authentication oAuth2Authentication) {
        ObjectMapper mapper = new ObjectMapper();
        SysUser user = null;
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
        LinkedHashMap tokenDetails = (LinkedHashMap)token.getDetails();
        LinkedHashMap p = (LinkedHashMap) tokenDetails.get("principal");
        LinkedHashMap userInfo = (LinkedHashMap) p.get("sysUser");
        if(!CollectionUtils.isEmpty(userInfo)) {
            user = mapper.convertValue(userInfo, SysUser.class);
            System.out.println(user);
        }
        return user;
    }

    public void setCurrentUser(ZsSurveyBaseEntity entity, OAuth2Authentication oAuth2Authentication){
        SysUser member = currentUser(oAuth2Authentication);
        entity.setCreatorId(member.getUid());
        entity.setCreator(new SysUser(member.getUid(), member.getUsername()));
    }

    public SysUser currentUser() {
        return currentUser((OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication());
    }
}
