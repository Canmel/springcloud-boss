package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsAches;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.ZsExamMapper;
import com.camel.survey.model.ZsDelivery;
import com.camel.survey.model.ZsExam;
import com.camel.survey.service.ZsDeliveryService;
import com.camel.survey.service.ZsExamService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
 *                ┃┫┫    ┃┫┫    @date 2019-12-12
 *                ┗┻┛    ┗┻┛
 */
@Service
public class ZsExamServiceImpl extends ServiceImpl<ZsExamMapper, ZsExam> implements ZsExamService {

    @Autowired
    private ZsExamMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZsDeliveryService deliveryService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsExam> selectPage(ZsExam entity, OAuth2Authentication oAuth2Authentication) {
        SysUser member = applicationToolsUtils.currentUser(oAuth2Authentication);
        Wrapper<ZsDelivery> wrapper = new EntityWrapper();
        wrapper.eq("creator", member.getUid());
        wrapper.eq("ach", ZsAches.APPLY.getValue());
        List<ZsDelivery> deliveries = deliveryService.selectList(wrapper);
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });

        List<ZsExam> zsExams = pageInfo.getList();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            zsExams.forEach(zsExam -> {
                zsExam.setIsDelivery(ZsYesOrNo.NO);
                deliveries.forEach(delivery -> {
                    if (delivery.getExamId().equals(zsExam.getId())) {
                        zsExam.setIsDelivery(ZsYesOrNo.YES);
                    }
                });
            });
        }
        return pageInfo;
    }

    @Override
    public Result save(ZsExam entity, OAuth2Authentication oAuth2Authentication) {
        applicationToolsUtils.setCurrentUser(entity, oAuth2Authentication);
        if (insert(entity)) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public Result delivery(Integer id, OAuth2Authentication oAuth2Authentication) {
        SysUser member = applicationToolsUtils.currentUser(oAuth2Authentication);
        Wrapper<ZsDelivery> deliveryWrapper = new EntityWrapper<>();
        deliveryWrapper.eq("exam_id", id);
        deliveryWrapper.eq("creator", member.getUid());
        deliveryWrapper.eq("ach", ZsAches.APPLY.getValue());
        if (deliveryService.selectCount(deliveryWrapper) > 0) {
            return ResultUtil.success("已经报名,无需重复提交");
        }
        ZsDelivery delivery = new ZsDelivery(id);
        delivery.setCreator(new SysUser(member.getUid()));
        delivery.setCreatorId(member.getUid());
        if (deliveryService.insert(delivery)) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(ResultEnum.BAD_REQUEST);
    }

    @Override
    public Result all() {
        Wrapper<ZsExam> examWrapper = new EntityWrapper<>();
        examWrapper.eq("status", ZsStatus.CREATED.getValue());
        return ResultUtil.success(selectList(examWrapper));
    }
}
