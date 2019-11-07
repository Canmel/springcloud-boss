package com.camel.oa.service.impl;

import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ZsProjectStatus;
import com.camel.oa.enums.ZsTalentederStatus;
import com.camel.oa.model.BaseOaEntity;
import com.camel.oa.model.ZsProject;
import com.camel.oa.model.ZsTalenteder;
import com.camel.oa.mapper.ZsTalentederMapper;
import com.camel.oa.service.ZsTalentederService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.utils.ApplicationToolsUtils;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
 * ┃┫┫    ┃┫┫    @date 2019-10-30
 * ┗┻┛    ┗┻┛
 */
@Service
public class ZsTalentederServiceImpl extends ServiceImpl<ZsTalentederMapper, ZsTalenteder> implements ZsTalentederService {

    @Autowired
    private ZsTalentederMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public Result save(ZsTalenteder entity, OAuth2Authentication oAuth2Authentication) {
        entity.setStatus(ZsTalentederStatus.NORMAL);
        applicationToolsUtils.setCreator(oAuth2Authentication, entity);
        if (mapper.insert(entity) > -1) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public PageInfo selectPage(ZsTalenteder entity) {
        return applicationToolsUtils.selectPage(entity, () -> {
            mapper.list(entity);
        });
    }
}
