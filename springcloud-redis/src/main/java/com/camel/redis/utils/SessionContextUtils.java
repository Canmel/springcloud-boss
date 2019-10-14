package com.camel.redis.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;

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
 * <Seesion工具类>
 *
 * @author baily
 * @date 2019/7/7
 * @since 1.0
 **/
public class SessionContextUtils {
    private final static SessionContextUtils INSTANCE = new SessionContextUtils();

    public SessionContextUtils() {
    }

    public static SessionContextUtils getInstance() {
        return INSTANCE;
    }

    public Object currentUser(RedisTemplate redisTemplate, String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        byte[] cu = (byte[]) operations.get(key);
        return SerizlizeUtil.unserizlize(cu);
    }
}
