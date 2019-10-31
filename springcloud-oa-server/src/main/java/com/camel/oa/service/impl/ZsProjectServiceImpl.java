package com.camel.oa.service.impl;

import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ResourceStatus;
import com.camel.oa.enums.ZsProjectStatus;
import com.camel.oa.model.Resource;
import com.camel.oa.model.ZsComment;
import com.camel.oa.model.ZsProject;
import com.camel.oa.mapper.ZsProjectMapper;
import com.camel.oa.service.ZsProjectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.utils.ApplicationToolsUtils;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        <智慧招商项目 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-10-22
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsProjectServiceImpl extends ServiceImpl<ZsProjectMapper, ZsProject> implements ZsProjectService {

    @Autowired
    private ZsProjectMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsProject> selectPage(ZsProject entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<SysUser> users = applicationToolsUtils.allUsers();
        List<ZsProject> projectList = pageInfo.getList();
        projectList.forEach(project -> {
            project.setCreator(applicationToolsUtils.getUser(project.getCreator().getUid()));
        });
        pageInfo.setList(projectList);
        return pageInfo;
    }

    @Override
    public Result save(ZsProject entity, OAuth2Authentication authentication) {
        entity.setStatus(ZsProjectStatus.NORMAL);
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, authentication.getName());
        entity.setCreator(new SysUser(member.getId()));
        entity.setCreatorId(member.getId());
        if (mapper.insert(entity) > -1) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }
}