package com.camel.survey.service.impl;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.mapper.TelProtectionMapper;
import com.camel.survey.model.TelProtection;
import com.camel.survey.service.TelProtectionService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TelProtectionServiceImpl implements TelProtectionService {
    @Resource
    private ApplicationToolsUtils applicationToolsUtils;

    @Resource
    private TelProtectionMapper telProtectionMapper;

    /**
     * 供应商：分页查询号码列表
     * @param telProtection 查询条件
     * @return Result
     */
    @Override
    public PageInfo<TelProtection> queryByPid(TelProtection telProtection) {
        PageHelper.startPage(telProtection.getPageNum(),telProtection.getPageSize());
        List<TelProtection> list = telProtectionMapper.selectByPid(telProtection);
        return new PageInfo<TelProtection>(list);
    }
    /**
     * 供应商：分页查询项目列表
     * @param telProtection 查询条件
     * @return Result
     */
    @Override
    public PageInfo<TelProtection> queryByFid(TelProtection telProtection) {
        PageHelper.startPage(telProtection.getPageNum(),telProtection.getPageSize());
        List<TelProtection> list = telProtectionMapper.selectByFid(telProtection);
        return new PageInfo<TelProtection>(list);
    }

    /**
     * 供应商：修改项目
     * @param surveyId, id 修改条件
     * @return Result
     */
    @Override
    public boolean modifiByTid(Integer surveyId, Integer id) {
        telProtectionMapper.updateByTid(surveyId, id);
        return true;
    }


    @Override
    public PageInfo<TelProtection> grantTelList(TelProtection telProtection) {
        PageHelper.startPage(telProtection.getPageNum(),telProtection.getPageSize());
        List<TelProtection> list = telProtectionMapper.grantTelList(telProtection);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<SysUser> partnerList(SysUser sysUser,Integer telId) {
        PageHelper.startPage(sysUser.getPageNum(),sysUser.getPageSize());
        List<SysUser> list = telProtectionMapper.partnerList(sysUser,telId);
        return new PageInfo<>(list);
    }

    @Override
    public Integer isExist(Integer uid, Integer telId) {
        return telProtectionMapper.isExist(uid,telId);
    }

    @Override
    public Result grant(TelProtection telProtection) {
        Integer res = telProtectionMapper.updatePartner(telProtection);
        if (res > 0){
            return ResultUtil.success("授权成功");
        }
        return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"修改失败");
    }

    @Override
    public Result revoke(TelProtection telProtection) {
        Boolean flag = telProtectionMapper.delPromise(telProtection);
        if (flag){
            return ResultUtil.success("撤销成功");
        }
        return ResultUtil.error(ResultEnum.UNKONW_ERROR);
    }

    @Override
    public List<String> getTelListByUserId(Integer projectId) {
        Integer uid = applicationToolsUtils.currentUser().getUid();
        return telProtectionMapper.getTelByUserId(uid,projectId);
    }

}
