package com.camel.survey.service.impl;

import com.camel.survey.model.ZsDelivery;
import com.camel.survey.model.ZsSign;
import com.camel.survey.mapper.ZsSignMapper;
import com.camel.survey.service.ZsSignService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
}
