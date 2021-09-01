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
import com.camel.survey.model.ZsProject;
import com.camel.survey.service.TelProtectionService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.CompanyVo;
import com.camel.survey.vo.FinalCusVo;
import com.camel.survey.vo.NumberVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
    public PageInfo<ZsProject> queryByFid(TelProtection telProtection) {
        PageHelper.startPage(telProtection.getPageNum(),telProtection.getPageSize());
        List<ZsProject> list = telProtectionMapper.selectByFid(telProtection);
        return new PageInfo<ZsProject>(list);
    }

    /**
     * 供应商：修改项目
     * @param projectId, id 修改条件
     * @return Result
     */
    @Override
    public Result modifiByTid(Integer projectId, Integer id) {
        int i = telProtectionMapper.updateByTid(projectId, id);
        if (i > 0) {
            return ResultUtil.success("修改成功");
        }
        return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"修改失败");
    }

    /**
     * 供应商：判断电话是否绑定了项目
     * @param projectId 修改条件
     * @param id
     * @return boolean
     */
    @Override
    public Integer hasProject(Integer projectId, Integer id) {
        return telProtectionMapper.hasProject(projectId,id);
    }

    /**
     * 供应商：撤销授权
     * @param telProtection 修改条件
     * @return Result
     */
    @Override
    public Result removeProject(TelProtection telProtection) {
        Integer res = telProtectionMapper.deleteProject(telProtection);
        if (res > 0){
            return ResultUtil.success("撤销成功");
        }
        return ResultUtil.error(ResultEnum.UNKONW_ERROR);
    }

    @Override
    public PageInfo<TelProtection> grantTelList(TelProtection telProtection) {
        PageHelper.startPage(telProtection.getPageNum(),telProtection.getPageSize());
        List<TelProtection> list = telProtectionMapper.grantTelList(telProtection);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<CompanyVo> partnerList(SysCompany sysCompany, Integer telId) {
        PageHelper.startPage(sysCompany.getPageNum(),sysCompany.getPageSize());
        List<CompanyVo> list = telProtectionMapper.partnerList(sysCompany,telId);
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
    @Transactional
    public Result revoke(TelProtection telProtection) {
        Integer res = telProtectionMapper.delPromise(telProtection);
        if (res > 0){
            Integer integer = telProtectionMapper.deleteProject(telProtection);
            if(integer >= 0){
                return ResultUtil.success("撤销成功");
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResultUtil.error(ResultEnum.UNKONW_ERROR);
    }

    @Override
    public PageInfo<JSONArray> all(NumberVo numberVo) {
        String r = HttpUtil.get("http://" + baseUrl + "/yscrm/v2/infs/getpstnnumber.json");
        JSONObject o = JSONUtil.parseObj(r);
        JSONArray array = o.getJSONArray("info");

        if (CollectionUtils.isEmpty(array)){
            ArrayList<JSONArray> telList = new ArrayList();
            return new PageInfo<>(telList);
        }

        if (!StringUtils.isEmpty(numberVo.getTel())){
            boolean contains = array.contains(numberVo.getTel().trim());
            if (contains){
                ArrayList<String> telList = new ArrayList();
                telList.add(numberVo.getTel());
                Page page = new Page(numberVo.getPageNum(), numberVo.getPageSize());
                //从链表中截取需要显示的子链表，并加入到Page
                page.addAll(telList);
                //以Page创建PageInfo
                PageInfo<JSONArray> pageInfo = new PageInfo<JSONArray>(page);
                return pageInfo;
            }else {
                return null;
            }
        }
        //创建Page类
        Page page = new Page(numberVo.getPageNum(), numberVo.getPageSize());
        //为Page类中的total属性赋值
        int total = array.size();
        page.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (numberVo.getPageNum() - 1) * numberVo.getPageSize();
        int endIndex = Math.min(startIndex + numberVo.getPageSize(),total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(array.subList(startIndex,endIndex));
        //以Page创建PageInfo
        PageInfo<JSONArray> pageInfo = new PageInfo<JSONArray>(page);
        return pageInfo;
    }

    @Override
    public PageInfo<FinalCusVo> finalList(SysUser sysUser,String tel) {
        PageHelper.startPage(sysUser.getPageNum(),sysUser.getPageSize());
        List<FinalCusVo> sysUsers = telProtectionMapper.finalList(sysUser,tel);
        return new PageInfo<>(sysUsers);
    }

    @Override
    @Transactional
    public Integer revokeTel(Integer id) {
        return telProtectionMapper.revokeTel(id);
    }

    @Override
    public Result grantFinal(TelProtection telProtection) {
        TelProtection protection = telProtectionMapper.selectBytel(telProtection.getTel());
        if (protection != null){
            if (telProtectionMapper.updateFinal(telProtection) > 0) {
                return ResultUtil.success("修改成功");
            }
            return ResultUtil.error(ResultEnum.SERVICE_ERROR);
        }
        Integer res = telProtectionMapper.insertFinal(telProtection);
        if (res < 0){
            return ResultUtil.error(ResultEnum.SERVICE_ERROR);
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
    public List<TelProtection> getPartnerName(String tel) {
        TelProtection telProtection = new TelProtection();
        telProtection.setTel(tel);
        return telProtectionMapper.grantTelList(telProtection);
    }

    @Override
    public List<String> getTelListByProId(Integer projectId) {
        return telProtectionMapper.getTelByProId(projectId);
    }

}
