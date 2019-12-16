package com.camel.survey.service.impl;

import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.survey.model.ZsDelivery;
import com.camel.survey.mapper.ZsDeliveryMapper;
import com.camel.survey.model.ZsExam;
import com.camel.survey.service.ZsDeliveryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
 *               ┃     ┃        <考核投递记录 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-12
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsDeliveryServiceImpl extends ServiceImpl<ZsDeliveryMapper, ZsDelivery> implements ZsDeliveryService {

    @Autowired
    private ZsDeliveryMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsDelivery> selectPage(ZsDelivery entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<ZsDelivery> deliveries = pageInfo.getList();
        deliveries.forEach(zsDelivery -> {
            zsDelivery.setCreator(applicationToolsUtils.getUser(zsDelivery.getCreatorId()));
        });
        return pageInfo;
    }

    @Override
    public Result save(ZsExam entity, OAuth2Authentication oAuth2Authentication) {
        return null;
    }
}
