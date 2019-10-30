package com.camel.oa.utils;

import com.camel.core.model.SysUser;
import com.camel.redis.utils.SerizlizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

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
}
