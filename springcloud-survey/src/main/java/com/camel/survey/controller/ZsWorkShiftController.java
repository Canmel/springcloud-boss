package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.model.ZsWorkShift;
import com.camel.survey.service.ZsWorkShiftService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-04-14
 */
@RestController
@RequestMapping("/zsWorkShift")
public class ZsWorkShiftController extends BaseCommonController {

    @Autowired
    private ZsWorkShiftService service;

    @Autowired
    private ApplicationToolsUtils applicationUtils;

    /**
     * 分页查询
     * @param entity
     * @param
     * @return
     */
    @GetMapping
    public Result index(ZsWorkShift entity, OAuth2Authentication authentication) {
        if(!isAdmin(authentication.getAuthorities())) {
            entity.setStartDate(new Date().toString());
        }
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 获取近期定点调查班次列表
     * @param entity
     * @param openId
     * @return
     */
    @AuthIgnore
    @GetMapping("/all")
    public Result all(ZsWorkShift entity, String openId) {
        return ResultUtil.success(service.all(entity, openId));
    }

    /**
     * 新建保存
     * @param entity
     * @param principal
     */
    @PostMapping
    public Result save(ZsWorkShift entity, Principal principal) {
        Wrapper<ZsWorkShift> zsWorkshift = new EntityWrapper<>();
        zsWorkshift.eq("cname",entity.getCname());
        int count = service.selectCount(zsWorkshift);
        if( count>0 ){
            return ResultUtil.success("该班次已存在，请重新输入！！",false);
        };
        return service.saveWorkShift(entity);
    }

    /**
     * 查看详细
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 编辑 更新
     * @param entity
     */
    @PutMapping
    public Result update(@RequestBody ZsWorkShift entity) {
        return  service.updateWorkShift(entity);
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
     * 根据问卷和用户信息获取班次
     * @param id
     * @return
     */
    @GetMapping("/current/{id}")
    public Result current(@PathVariable Integer id) {
        SysUser member = applicationUtils.currentUser();
        return ResultUtil.success(service.selectByUidandSurveyId(member.getUid(),id));
    }

    /**
     * 获取service
     */
    @Override
    public IService getiService() {
        return service;
    }

    /**
     * 获取模块名称
     */
    @Override
    public String getMouduleName() {
        return "班次";
    }


    public boolean isAdmin(Collection<GrantedAuthority> list) {
        for (GrantedAuthority authority:list) {
            if(authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}

