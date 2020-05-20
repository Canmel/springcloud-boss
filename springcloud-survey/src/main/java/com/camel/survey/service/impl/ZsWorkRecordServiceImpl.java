package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsSign;
import com.camel.survey.model.ZsWorkRecord;
import com.camel.survey.mapper.ZsWorkRecordMapper;
import com.camel.survey.model.ZsWorkShift;
import com.camel.survey.service.ZsSignService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsWorkRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.service.ZsWorkShiftService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
@Service
public class ZsWorkRecordServiceImpl extends ServiceImpl<ZsWorkRecordMapper, ZsWorkRecord> implements ZsWorkRecordService {

    @Autowired
    private ZsWorkRecordMapper mapper;

    @Autowired
    private ZsSurveyService service;

    @Autowired
    private ZsWorkShiftService zsWorkservice;

    @Autowired
    private ZsSignService zsSignService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;


    @Override
    public PageInfo<ZsWorkRecord> selectPage(ZsWorkRecord entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<ZsWorkRecord> deliveries = pageInfo.getList();
        deliveries.forEach(zsDelivery -> {
            zsDelivery.setCreator(applicationToolsUtils.getUser(zsDelivery.getCreatorId()));
        });
        return pageInfo;
    }

    @Override
    public Result start(ZsWorkRecord entity, OAuth2Authentication oAuth2Authentication) {
        Wrapper<ZsWorkRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("ws_id",entity.getWsId());
        wrapper.eq("creator",entity.getCreatorId());
        if(mapper.selectCount(wrapper)>0){
            return ResultUtil.success("您已提交过了，请勿重复提交");
        };
        Result result = service.sign(entity.getSurveyId(), oAuth2Authentication);
        if(result.getMsg().equals("投递成功")){
            mapper.insert(entity);

        }
        if(result.getMsg().equals("您已经投递过了，无需重复提交") || result.getMsg().equals("您已经投递过了，并且已经审核通过，无需重复提交")){
            result.setMsg("投递成功");
            mapper.insert(entity);
        }
        return result;
    }

    @Override
    public Result updateSignW(ZsWorkRecord entity) {
        mapper.updateById(entity);
        ZsWorkShift zsWorkShift = zsWorkservice.selectById(entity.getWsId());
        ZsSign zsSign = new ZsSign();
        Wrapper<ZsSign> wrapper = new EntityWrapper<>();
        if(zsWorkShift != null){
            zsSign.setResult(entity.getResult());
            wrapper.eq("survey_id",zsWorkShift.getSurveyId());
            wrapper.eq("creator",entity.getCreator().getUid());
        }

        return ResultUtil.success("修改成功",zsSignService.update(zsSign,wrapper));
    }
}
