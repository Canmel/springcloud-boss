package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.ZsAnswerItemMapper;
import com.camel.survey.mapper.ZsAnswerMapper;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.ZsAnswerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
 *                ┃┫┫    ┃┫┫    @date 2019-12-17
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsAnswerServiceImpl extends ServiceImpl<ZsAnswerMapper, ZsAnswer> implements ZsAnswerService {

    @Autowired
    private ZsAnswerMapper mapper;

    @Autowired
    private ZsSurveyMapper surveyMapper;

    @Autowired
    private ZsAnswerItemMapper answerItemMapper;

    @Override
    public PageInfo<ZsAnswer> selectPage(ZsAnswer entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public ZsAnswer details(Integer id) {
        return mapper.details(id);
    }

    @Override
    @Transactional
    public Result invalid(Integer id) {
//        mapper.invalidItems()
        /**
         * 修改子表样本数量
         */
        ZsAnswer zsAnswer = this.selectById(id);
        ZsSurvey zsSurvey = surveyMapper.selectById(zsAnswer.getSurveyId());
        if(!ObjectUtils.isEmpty(zsAnswer.getValid()) && zsAnswer.getValid() == ZsYesOrNo.NO) {
            zsAnswer.setValid(ZsYesOrNo.YES);
            answerItemMapper.chageInvalidByAnswer(id, ZsYesOrNo.YES.getCode());
            /**
             * 修改当前样本数量
             */
            zsSurvey.setCurrentNum(zsSurvey.getCurrentNum() + 1);
        }else {
            zsAnswer.setValid(ZsYesOrNo.NO);
            answerItemMapper.chageInvalidByAnswer(id, ZsYesOrNo.NO.getCode());
            /**
             * 修改当前样本数量
             */
            zsSurvey.setCurrentNum(zsSurvey.getCurrentNum() - 1);
        }

        surveyMapper.updateById(zsSurvey);
        /**
         * 修改主表状态
         */
        mapper.updateById(zsAnswer);
        return ResultUtil.success("样本状态变更成功");
    }

    @Override
    @Transactional
    public Result deleteAnswer(Integer id) {
        mapper.deleteById(id);
        Wrapper<ZsAnswerItem> answerItemWrapper = new EntityWrapper<>();
        answerItemWrapper.eq("answer_id", id);
        answerItemMapper.delete(answerItemWrapper);
        return ResultUtil.success("删除成功");
    }
}
