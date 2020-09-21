package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.config.WxConstants;
import com.camel.survey.enums.ZsApply;
import com.camel.survey.enums.ZsGain;
import com.camel.survey.model.ZsCashApply;
import com.camel.survey.mapper.ZsCashApplyMapper;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsCashApplyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.utils.WechatpayUtil;
import com.camel.survey.vo.TransfersDto;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    public static final String MCH_NAME = "赛炜大数据服务有限公司";

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private ZsCashApplyMapper mapper;

    @Autowired
    private ZsWorkService zsWorkService;

    @Autowired
    private WxConstants wxConstants;

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

    @Override
    public PageInfo<ZsCashApply> selectPage(ZsCashApply entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<ZsCashApply> list = pageInfo.getList();
        list.forEach(e -> {
            e.setZsWorkId(e.getWorks().split(","));
        });
        return pageInfo;
    }

    @Override
    public Result pass(Integer id) {
        ZsCashApply cashApply = mapper.selectById(id);
        if(!cashApply.getStatus().equals(ZsApply.NORMAL)) {
            return ResultUtil.success("提交数据重复或您选择的数据不在审核状态");
        }
        String appkey = wxConstants.getAppkey();// 微信商户秘钥, 根据实际情况填写
        String certPath = "apiclient_cert.p12";// 微信商户证书路径, 根据实际情况填写

        TransfersDto model = new TransfersDto();// 微信接口请求参数, 根据实际情况填写
        model.setMch_appid(wxConstants.getAppid()); // 申请商户号的appid或商户号绑定的appid
        model.setMchid(wxConstants.getMchid()); // 商户号
        model.setMch_name(wxConstants.getMchname()); // 商户名称
        model.setOpenid(cashApply.getOpenid()); // 商户appid下，某用户的openid
        model.setAmount(cashApply.getAmount()); // 企业付款金额，这里单位为元
        model.setDesc(cashApply.buildDesc());

        cashApply.setStatus(ZsApply.SUCESS);
        Result iResult = WechatpayUtil.doTransfers(appkey, certPath, model);
        if(iResult.isSuccess()) {
            mapper.updateById(cashApply);
        }
        LoggerFactory.getLogger(this.getClass()).info(iResult.toString());
        return iResult;
    }

    @Override
    public Result reject(Integer id) {
        ZsCashApply apply = selectById(id);
        apply.setStatus(ZsApply.FAILD);
        mapper.updateById(apply);
        return ResultUtil.success("驳回申请成功");
    }

    @Override
    public String selectTimeRange() {
        return mapper.selectTimeRange();
    }
}
