package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.mapper.ZsProjectMapper;
import com.camel.survey.model.ZsProject;
import com.camel.survey.service.ZsProjectService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
 *               ┃     ┃        < 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-04
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
        List<ZsProject> projectList = pageInfo.getList();
        projectList.forEach(project -> {
            List<SysUser> users = applicationToolsUtils.allUsers();
            users.forEach(sysUser -> {
                if (sysUser.getUid().equals(project.getCreatorId())) {
                    project.setCreator(sysUser);
                }
            });
        });
        return pageInfo;
    }

    @Override
    public PageInfo<ZsProject> selectPageDev(ZsProject entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.listByUid(applicationToolsUtils.currentUser().getUid());
        });
        List<ZsProject> projectList = pageInfo.getList();
        projectList.forEach(project -> {
            List<SysUser> users = applicationToolsUtils.allUsers();
            users.forEach(sysUser -> {
                if (sysUser.getUid().equals(project.getCreatorId())) {
                    project.setCreator(sysUser);
                }
            });
        });
        return pageInfo;
    }

    @Override
    public Result save(ZsProject entity, OAuth2Authentication oAuth2Authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        entity.setCreator(new SysUser(member.getId()));
        entity.setCreatorId(member.getId());
        if (insert(entity)) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public ZsProject selectById(Serializable id) {
        ZsProject project = mapper.selectById(id);
        applicationToolsUtils.allUsers().forEach(sysUser -> {
            if (sysUser.getUid().equals(project.getCreatorId())) {
                project.setCreator(sysUser);
            }
        });
        return project;
    }

    @Override
    public ZsProject getByNameFromList(String pname, List<ZsProject> projects) {
        for (ZsProject project: projects) {
            if(project.getName().equals(pname)) {
                return project;
            }
        }
        return null;
    }
}
