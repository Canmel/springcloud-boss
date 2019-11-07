package com.camel.oa.utils;

import com.camel.common.entity.Member;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.model.BaseOaEntity;
import com.camel.oa.model.ZsTalenteder;
import com.camel.redis.utils.SerizlizeUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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

    public void setCreator(OAuth2Authentication authentication, BaseOaEntity entity) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, authentication.getName());
        entity.setCreator(new SysUser(member.getId()));
        entity.setCreatorId(member.getId());
    }

    /**
     * 分页信息查询 顺便把创建者的用户信息添加进来
     * @param entity
     * @param iSelect
     * @return
     */
    public PageInfo<BaseOaEntity> selectPage(BaseOaEntity entity, ISelect iSelect){
        PageInfo pageInfo = PaginationUtil.startPage(entity, iSelect);
        List<BaseOaEntity> projectList = pageInfo.getList();
        projectList.forEach(baseOaEntity -> {
            if(!ObjectUtils.isEmpty(baseOaEntity.getCreator())) {
                baseOaEntity.setCreator(getUser(baseOaEntity.getCreator().getUid()));
            }
        });
        return pageInfo;
    }

    public Member currentUser(OAuth2Authentication oAuth2Authentication) {
        return  (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
    }
}
