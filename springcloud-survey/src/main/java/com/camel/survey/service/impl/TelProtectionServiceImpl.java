package com.camel.survey.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.mapper.SysUserMapper;
import com.camel.survey.mapper.TelProtectionMapper;
import com.camel.survey.model.TelProtection;
import com.camel.survey.service.TelProtectionService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.FinalCusVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TelProtectionServiceImpl extends ServiceImpl<TelProtectionMapper, TelProtection> implements TelProtectionService {
    @Resource
    private ApplicationToolsUtils applicationToolsUtils;

    @Resource
    private TelProtectionMapper telProtectionMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Value("${cti.baseUrl}")
    public String baseUrl;

    /**
     * 供应商：分页查询号码列表
     * @param telProtection 查询条件
     * @return Result
     */
    @Override
    public PageInfo<TelProtection> queryByPid(TelProtection telProtection) {
        SysUser sysUser = applicationToolsUtils.currentUser();
        SysUser user = sysUserMapper.selectByUid(sysUser.getUid());
        telProtection.setPartnerId(user.getCompanyId());
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
     * @param projectId, id 修改条件
     * @return Result
     */
    @Override
    public boolean modifiByTid(Integer projectId, Integer id) {
        telProtectionMapper.updateByTid(projectId, id);
        return true;
    }


    @Override
    public PageInfo<TelProtection> grantTelList(TelProtection telProtection) {
        PageHelper.startPage(telProtection.getPageNum(),telProtection.getPageSize());
        List<TelProtection> list = telProtectionMapper.grantTelList(telProtection);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<SysCompany> partnerList(SysCompany sysCompany) {
        PageHelper.startPage(sysCompany.getPageNum(),sysCompany.getPageSize());
        List<SysCompany> list = telProtectionMapper.partnerList(sysCompany);
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
        Integer res = telProtectionMapper.delPromise(telProtection);
        if (res > 0){
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
    public PageInfo<FinalCusVo> finalList(SysUser sysUser) {
        PageHelper.startPage(sysUser.getPageNum(),sysUser.getPageSize());
        List<FinalCusVo> sysUsers = telProtectionMapper.finalList(sysUser);
        return new PageInfo<>(sysUsers);
    }

    @Override
    @Transactional
    public Integer revokeTel(Integer id) {
        return telProtectionMapper.revokeTel(id);
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
    
    @Override
    public List<String> getTelListByProId(Integer projectId) {
        return telProtectionMapper.getTelByProId(projectId);
    }

}
