package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.QuestionStatus;
import com.camel.oa.mapper.QuestionMapper;
import com.camel.oa.model.Question;
import com.camel.oa.service.QuestionService;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * ┃┫┫    ┃┫┫    @date 2019-10-21
 * ┗┻┛    ┗┻┛
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<Question> selectPage(Question entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    @Transactional
    public Result save(Question entity, Authentication authentication) {
        entity.setStatus(QuestionStatus.NORMAL);
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, authentication.getName());
        entity.setCreator(new SysUser(member.getId()));
        entity.setCreatorId(member.getId());
        if (mapper.insert(entity) > -1) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }
}
