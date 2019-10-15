package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.enums.ResourceStatus;
import com.camel.oa.mapper.ResourceMapper;
import com.camel.oa.model.Resource;
import com.camel.oa.service.ResourceService;
import com.camel.oa.utils.ApplicationToolsUtils;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
 * <资源服务>
 * @author baily
 * @since 1.0
 * @date 2019/10/15
 **/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Autowired
    private ResourceMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<Resource> selectPage(Resource entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<SysUser> users = applicationToolsUtils.allUsers();
        List<Resource> resourceList = pageInfo.getList();
        resourceList.forEach(resource -> {
            users.forEach(user -> {
                if (!ObjectUtils.isEmpty(resource.getCreator()) && user.getUid().equals(resource.getCreator().getUid())) {
                    resource.setCreator(user);
                }
            });
        });
        return pageInfo;
    }

    @Override
    public boolean save(Resource resource, OAuth2Authentication authentication) {
        resource.setStatus(ResourceStatus.NORMAL);
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, authentication.getName());
        resource.setCreator(new SysUser(member.getId()));
        return this.insert(resource);
    }
}
