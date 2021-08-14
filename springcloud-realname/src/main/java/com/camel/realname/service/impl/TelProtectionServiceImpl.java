package com.camel.realname.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.mapper.TelProtectionMapper;
import com.camel.realname.model.TelProtection;
import com.camel.realname.service.TelProtectionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TelProtectionServiceImpl implements TelProtectionService {

    @Resource
    private TelProtectionMapper telProtectionMapper;

    @Value("${cti.baseUrl}")
    public String baseUrl;

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
    public PageInfo<SysCompany> partnerList(SysCompany sysCompany,Integer telId) {
        PageHelper.startPage(sysCompany.getPageNum(),sysCompany.getPageSize());
        List<SysCompany> list = telProtectionMapper.partnerList(sysCompany,telId);
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
    public JSONArray all() {
        String r = HttpUtil.get("http://" + baseUrl + "/yscrm/v2/infs/getpstnnumber.json");
        JSONObject o = JSONUtil.parseObj(r);
        JSONArray array = o.getJSONArray("info");
        return array;
    }

    @Override
    public PageInfo<SysCompany> finalList(SysCompany sysCompany) {
        PageHelper.startPage(sysCompany.getPageNum(),sysCompany.getPageSize());
        List<SysCompany> companies = telProtectionMapper.finalList(sysCompany);
        return new PageInfo<>(companies);
    }

    @Override
    public Result grantFinal(TelProtection telProtection) {
        Integer res = telProtectionMapper.insertFinal(telProtection);
        if (res < 0){
            return ResultUtil.error(ResultEnum.RESOURCESNOTFOUND);
        }
        return ResultUtil.success("绑定成功");
    }

    @Override
    public Result getFinalName(String tel) {
        if (StringUtils.isEmpty(tel)){
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"接入号为空");
        }
        return ResultUtil.success("查询成功",telProtectionMapper.selectByTel(tel));
    }
}
