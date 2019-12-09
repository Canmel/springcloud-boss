package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.mapper.ZsQuestionMapper;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.ZsOptionService;
import com.camel.survey.service.ZsQuestionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.vo.ZsQuestionSave;
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
 *               ┃     ┃        < 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-09
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsQuestionServiceImpl extends ServiceImpl<ZsQuestionMapper, ZsQuestion> implements ZsQuestionService {

    @Autowired
    private ZsQuestionMapper mapper;

    @Autowired
    private ZsOptionService zsOptionService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<ZsQuestion> selectPage(ZsQuestion entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public Result save(ZsQuestionSave entity, OAuth2Authentication oAuth2Authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        // 保存问题
        entity.getZsQuestions().forEach(q -> {
            q.setCreatorId(member.getId());
            mapper.insert(q);
            List<ZsOption> optionList = entity.optionsfilterAndBuildInsertParams(q);
            // 保存选项
            if (CollectionUtils.isNotEmpty(optionList)) {
                zsOptionService.insertBatch(optionList);
            }
        });
        return ResultUtil.success("新增成功");

    }


}
