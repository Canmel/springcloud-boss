package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ZsCommentStatus;
import com.camel.oa.mapper.ZsCommentMapper;
import com.camel.oa.model.ZsComment;
import com.camel.oa.service.ZsCommentService;
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
 * ┗━┓     ┏━┛
 * ┃     ┃　Code is far away from bug with the animal protecting
 * ┃     ┃   神兽保佑,代码无bug
 * ┃     ┃
 * ┃     ┃
 * ┃     ┃        < 服务实现类>
 * ┃     ┃
 * ┃     ┗━━━━┓   @author baily
 * ┃          ┣┓
 * ┃          ┏┛  @since 1.0
 * ┗┓┓┏━━━━┳┓┏┛
 * ┃┫┫    ┃┫┫    @date 2019-10-23
 * ┗┻┛    ┗┻┛
 */
@Service
public class ZsCommentServiceImpl extends ServiceImpl<ZsCommentMapper, ZsComment> implements ZsCommentService {

    @Autowired
    private ZsCommentMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsComment> selectPage(ZsComment entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<SysUser> users = applicationToolsUtils.allUsers();
        List<ZsComment> zsComments = pageInfo.getList();
        zsComments.forEach(zsComment -> {
            users.forEach(user -> {
                if (user.getUid().equals(zsComment.getCreator().getUid())) {
                    zsComment.setCreator(user);
                }
            });
        });
        pageInfo.setList(zsComments);
        return pageInfo;
    }

    @Override
    public Result save(ZsComment entity, OAuth2Authentication authentication) {
        entity.setStatus(ZsCommentStatus.NORMAL);
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, authentication.getName());
        entity.setCreator(new SysUser(member.getId()));
        entity.setCreatorId(member.getId());
        if (mapper.insert(entity) > -1) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public List<ZsComment> list(ZsComment entity) {
        List<SysUser> users = applicationToolsUtils.allUsers();
        List<ZsComment> zsCommentList = mapper.list(entity);
        zsCommentList.forEach(zsComment -> {
            users.forEach(user -> {
                if (user.getUid().equals(zsComment.getCreator().getUid())) {
                    zsComment.setCreator(user);
                }
            });
        });
        return zsCommentList;
    }
}
