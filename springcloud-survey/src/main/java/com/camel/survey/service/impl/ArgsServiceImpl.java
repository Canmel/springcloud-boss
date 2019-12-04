package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.survey.model.Args;
import com.camel.survey.mapper.ArgsMapper;
import com.camel.survey.service.ArgsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
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
 * ┃┫┫    ┃┫┫    @date 2019-11-22
 * ┗┻┛    ┗┻┛
 */
@Service
public class ArgsServiceImpl extends ServiceImpl<ArgsMapper, Args> implements ArgsService {

    public static final String[] MAINARGSKEYS = {"sign_in_time", "sign_out_time", "advance_time", "delay_time", "sign_position_1", "sign_radius"};
    public static final String CODE_COLUM_NAME = "code";
    @Autowired
    private ArgsMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<Args> selectPage(Args entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<Args> argsList = pageInfo.getList();
        argsList.forEach(args -> {
            applicationToolsUtils.allUsers().forEach(sysUser -> {
                if(sysUser.getUid().equals(args.getCreatorId())) {
                    args.setCreator(sysUser);
                }
                if(sysUser.getUid().equals(args.getUpdatorId())) {
                    args.setUpdator(sysUser);
                }
            });
        });
        return pageInfo;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result save(Args entity, OAuth2Authentication oAuth2Authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        entity.setCreator(new SysUser(member.getId()));
        entity.setCreatorId(member.getId());
        entity.setUpdator(new SysUser(member.getId()));
        entity.setUpdatorId(member.getId());
        if (insert(entity)) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public List<Args> selectForMain() {
        Wrapper wrapper = new EntityWrapper<Args>();
        wrapper.in(CODE_COLUM_NAME, MAINARGSKEYS);
        return mapper.selectList(wrapper);
    }
}
