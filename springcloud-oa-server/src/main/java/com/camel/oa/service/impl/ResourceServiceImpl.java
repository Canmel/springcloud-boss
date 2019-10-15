package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.mapper.ResourceMapper;
import com.camel.oa.model.Resource;
import com.camel.oa.service.ResourceService;
import com.camel.oa.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-10-14
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Autowired
    private ResourceMapper mapper;

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
}
