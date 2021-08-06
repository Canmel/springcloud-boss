package com.camel.realname.service.impl;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.enums.NumberStatus;
import com.camel.realname.enums.ZsApplyStatus;
import com.camel.realname.mapper.ApplyNumberMapper;
import com.camel.realname.mapper.ApproveMapper;
import com.camel.realname.mapper.ZsCorpMapper;
import com.camel.realname.model.ApplyNumber;
import com.camel.realname.model.ApproveInfo;
import com.camel.realname.model.ZsCorp;
import com.camel.realname.service.ApproveService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ApproveServiceImpl implements ApproveService {
    @Resource
    private ApproveMapper approveMapper;

    @Resource
    private ZsCorpMapper zsCorpMapper;

    @Resource
    private ApplyNumberMapper applyNumberMapper;

    @Override
    public PageInfo<ApproveInfo> getPageList(ApproveInfo approveInfo) {
        PageHelper.startPage(approveInfo.getPageNum(),approveInfo.getPageSize());
        List<ApproveInfo> list = approveMapper.getList(approveInfo);
        return new PageInfo<ApproveInfo>(list);
    }

    @Override
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
                zsCorpMapper.updateCorp(zsCorp);
            }else if(approveInfo.getType().getCode() == 1){
                //  外呼号码
                ApplyNumber applyNumber = new ApplyNumber();
                applyNumber.setId(approveInfo.getUserId());
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
