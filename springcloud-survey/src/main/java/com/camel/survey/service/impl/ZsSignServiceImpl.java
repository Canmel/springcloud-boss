package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.enums.ResultEnum;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.mapper.ZsSignMapper;
import com.camel.survey.model.ZsDelivery;
import com.camel.survey.model.ZsSign;
import com.camel.survey.service.ZsDeliveryService;
import com.camel.survey.service.ZsSignService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ZsDynamicView;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 *               ┃     ┃        <问卷调查报名记录 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-13
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsSignServiceImpl extends ServiceImpl<ZsSignMapper, ZsSign> implements ZsSignService {

    @Autowired
    private ZsSignMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private ZsDeliveryService zsDeliveryService;

    @Autowired
    private ZsSurveyService zsSurveyService;

    @Override
    public PageInfo<ZsSign> selectPage(ZsSign entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        SysUser sysUser = applicationToolsUtils.currentUser();
        List<ZsSign> deliveries = pageInfo.getList();
        for (int i = 0; i < deliveries.size(); i++) {
            ZsSign zsDelivery = deliveries.get(i);
            zsDelivery.setCreator(applicationToolsUtils.getUser(zsDelivery.getCreatorId()));
            zsSurveyService.selectTotal(zsDelivery.getSurveyId(), sysUser.getUid(), zsDelivery);
        }
        return pageInfo;
    }

    @Override
    public Result selectByUserId(Integer id) {
        List<ZsDynamicView> zsDynamicViews = new ArrayList<>();

        PageInfo pageInfo = PaginationUtil.startPage(new BasePaginationEntity(), () -> {
            mapper.list(new ZsSign(id));
        });
        List<ZsSign> zsSigns = pageInfo.getList();

        for (int i = 0; i < zsSigns.size(); i++) {
            ZsSign zsSign = zsSigns.get(i);
            ZsDynamicView zsDynamicView = new ZsDynamicView(zsSign.getCreatedAt(), zsSign.getUsername(), "参加了 " + zsSign.getSurvey().getName(), zsSign.getSurveyId());
            zsDynamicViews.add(zsDynamicView);
        }

        return ResultUtil.success(zsDynamicViews);
    }

    @Override
    public ZsSign selectLastByUser(Integer id) {
        return mapper.selectLastByUser(id);
    }

    @Override
    public List<ZsSign> allInSurvey(Integer id) {
        return mapper.allInSurvey(id);
    }

    @Override
    public Result userZsSign(String userid) {
        Map<String,Integer> map = new HashMap<>();
        Wrapper<ZsSign> wrapperSign = new EntityWrapper<>();
        wrapperSign.eq("creator",userid);
        wrapperSign.eq("result",1);
        Wrapper<ZsDelivery> wrapperDelivery = new EntityWrapper<>();
        wrapperDelivery.eq("creator",userid);
        wrapperDelivery.eq("ach",1);
        Integer countSign = mapper.selectCount(wrapperSign);
        Integer countDelivery = zsDeliveryService.selectCount(wrapperDelivery);
        map.put("countSign",countSign);
        map.put("countDelivery",countDelivery);
        return new Result(ResultEnum.SUCCESS.getCode(), "操作成功",map, true);
    }
}
