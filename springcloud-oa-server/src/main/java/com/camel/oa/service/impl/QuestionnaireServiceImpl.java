package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.QuestionnaireStatus;
import com.camel.oa.mapper.QuestionnaireMapper;
import com.camel.oa.model.Questionnaire;
import com.camel.oa.service.QuestionnaireService;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

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
 * ┃     ┃        <调查问卷 服务实现类>
 * ┃     ┃
 * ┃     ┗━━━━┓   @author baily
 * ┃          ┣┓
 * ┃          ┏┛  @since 1.0
 * ┗┓┓┏━━━━┳┓┏┛
 * ┃┫┫    ┃┫┫    @date 2019-10-21
 * ┗┻┛    ┗┻┛
 */
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<Questionnaire> selectPage(Questionnaire entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public Result save(Questionnaire entity, OAuth2Authentication authentication) {
        entity.setStatus(QuestionnaireStatus.NORMAL);
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, authentication.getName());
        entity.setCreator(new SysUser(member.getId()));
        entity.setCreatorId(member.getId());
        if (mapper.insert(entity) > -1) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }
}
