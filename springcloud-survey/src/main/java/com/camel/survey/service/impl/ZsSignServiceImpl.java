package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.mapper.ZsSignMapper;
import com.camel.survey.model.ZsSign;
import com.camel.survey.service.ZsSignService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.ZsDynamicView;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public PageInfo<ZsSign> selectPage(ZsSign entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<ZsSign> deliveries = pageInfo.getList();
        deliveries.forEach(zsDelivery -> {
            zsDelivery.setCreator(applicationToolsUtils.getUser(zsDelivery.getCreatorId()));
        });
        return pageInfo;
    }

    @Override
    public Result selectByUserId(Integer id) {
        List<ZsDynamicView> zsDynamicViews = new ArrayList<>();

        PageInfo pageInfo = PaginationUtil.startPage(new BasePaginationEntity(), () -> {
            mapper.list(new ZsSign(id));
        });
        List<ZsSign> zsSigns = pageInfo.getList();

        zsSigns.forEach(zsSign -> {
            ZsDynamicView zsDynamicView = new ZsDynamicView(zsSign.getCreatedAt(), zsSign.getUsername(), "参加了 " + zsSign.getSurvey().getName());
            zsDynamicViews.add(zsDynamicView);
        });

        return ResultUtil.success(zsDynamicViews);
    }
}
