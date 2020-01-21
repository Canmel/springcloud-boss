package com.camel.system.config;

import com.camel.common.utils.SerizlizeUtil;
import com.camel.core.model.SysUser;
import com.camel.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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
 * <用户缓存配置>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@Component
public class SysUserCacheConfig {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    public void initSysUsers() {
        List<SysUser> sysUserList = sysUserService.all();
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        byte[] su = SerizlizeUtil.serialize(sysUserList);
        operations.set("ALL_SYS_USERS", su, 9999, TimeUnit.DAYS);
    }

}
