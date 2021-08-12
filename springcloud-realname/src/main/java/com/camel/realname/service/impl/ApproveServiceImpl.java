package com.camel.realname.service.impl;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.enums.NumberStatus;
import com.camel.realname.enums.ZsApplyStatus;
import com.camel.realname.enums.ZsStatus;
import com.camel.realname.enums.ZsYesOrNo;
import com.camel.realname.mapper.ApplyNumberMapper;
import com.camel.realname.mapper.ApproveMapper;
import com.camel.realname.mapper.ApproveOrderMapper;
import com.camel.realname.mapper.ZsCorpMapper;
import com.camel.realname.model.ApplyNumber;
import com.camel.realname.model.ApproveInfo;
import com.camel.realname.model.ApproveOrder;
import com.camel.realname.model.ZsCorp;
import com.camel.realname.service.ApproveService;
import com.camel.realname.utils.SnowflakeIdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ApproveServiceImpl implements ApproveService {
    @Resource
    private ApproveMapper approveMapper;

    @Resource
    private ZsCorpMapper zsCorpMapper;

    @Resource
    private ApproveOrderMapper approveOrderMapper;

    @Resource
    private ApplyNumberMapper applyNumberMapper;

    @Override
    public PageInfo<ApproveInfo> getPageList(ApproveInfo approveInfo) {
        PageHelper.startPage(approveInfo.getPageNum(),approveInfo.getPageSize());
        List<ApproveInfo> list = approveMapper.getList(approveInfo);
        return new PageInfo<ApproveInfo>(list);
    }

    @Override
    @Transactional
    public Result audit(ApproveInfo approveInfo) {
        if(approveInfo != null && approveInfo.getType() != null){
            if(approveInfo.getType().getCode() == 0){
                //  企业
                ZsCorp zsCorp = new ZsCorp();
                zsCorp.setUserId(approveInfo.getUserId());
                ZsApplyStatus apply = ZsApplyStatus.getEnumByCode(approveInfo.getApply().getCode());
                if(apply == null){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"状态码类型不存在");
                }
                zsCorp.setApply(apply);
                Integer res = zsCorpMapper.updateCorp(zsCorp);
                if(res > 0){
                    if(ZsApplyStatus.APPLY_SUCCESS.getCode().equals(approveInfo.getApply().getCode())){
                        //  审核通过，创建订单
                        ApproveOrder order = new ApproveOrder();
                        order.setId(SnowflakeIdWorker.generateId());
                        order.setUserId(approveInfo.getUserId());
                        order.setPrice(new BigDecimal("0.01"));
                        order.setSubject("号码授权订单");
                        order.setBody("号码授权");
                        order.setCreateTime(new Date());
                        order.setIsPay(ZsYesOrNo.NO);
                        order.setStatus(ZsStatus.CREATED);
                        Integer integer = approveOrderMapper.insertOrder(order);
                        if(integer <= 0){
                            return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"创建订单失败");
                        }
                    }
                }else{
                    return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"修改认证状态失败");
                }
            }else if(approveInfo.getType().getCode() == 1){
                //  外呼号码
                ApplyNumber applyNumber = new ApplyNumber();
                applyNumber.setId(approveInfo.getId());
                NumberStatus apply = NumberStatus.getEnumByCode(approveInfo.getApply().getCode());
                if(apply == null){
                    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"状态码类型不存在");
                }
                applyNumber.setStatus(apply);
                applyNumberMapper.updateById(applyNumber);
            }else if(approveInfo.getType().getCode() == 2){
                //  个人
            }
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
        return ResultUtil.error(ResultEnum.BAD_REQUEST);
    }

}
