package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.enums.ZsGain;
import com.camel.survey.model.ZsCashApply;
import com.camel.survey.mapper.ZsCashApplyMapper;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsCashApplyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 提现省钱 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-21
 */
@Service
public class ZsCashApplyServiceImpl extends ServiceImpl<ZsCashApplyMapper, ZsCashApply> implements ZsCashApplyService {

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private ZsCashApplyMapper mapper;

    @Autowired
    private ZsWorkService zsWorkService;

    @Override
    @Transactional
    public Result apply(ZsCashApply apply) {
        String [] works = apply.getWorks().split(",");
        if(StringUtils.isBlank(apply.getWorks())) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "您没有可提现额度");
        }
        Wrapper<ZsWork> workWrapper = new EntityWrapper<>();
        workWrapper.eq("gain", 0);
        workWrapper.in("id", works);

        int count = zsWorkService.selectCount(workWrapper);
        if(count < works.length) {
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "您申请的提现数据不正常");
        }
        List<ZsWork> zwList = new ArrayList<>();
        for (String w: works) {
            zwList.add(new ZsWork(Integer.parseInt(w), ZsGain.APPLY.getCode()));
        }
        List<ZsWork> workList = zsWorkService.selectList(workWrapper);
        Double balance = workList.stream().mapToDouble(ZsWork::getSalary).sum();
        apply.setAmount(balance);
        if ((mapper.insert(apply) > 0) && zsWorkService.updateBatchById(zwList)) {
            return ResultUtil.success("发起提现申请成功！审核成功后即可通过本公众号领取佣金");
        }else{
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "发起申请失败！");
        }
    }
}
