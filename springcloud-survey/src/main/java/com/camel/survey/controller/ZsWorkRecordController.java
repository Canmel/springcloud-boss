package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.feign.SpringCloudSystemFeignClient;
import com.camel.survey.model.ZsWorkRecord;
import com.camel.survey.service.ZsWorkRecordService;
import com.camel.survey.service.ZsWorkShiftService;
import com.camel.survey.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import com.camel.core.controller.BaseCommonController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/zsWorkRecord")
public class ZsWorkRecordController extends BaseCommonController {

    @Autowired
    private ZsWorkRecordService service;

    @Autowired
    private ZsWorkShiftService zsWorkShiftService;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    /**
     * 分页查询
     * @param entity
     * @return
     */
    @GetMapping
    public Result index(ZsWorkRecord entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 用户报名班次
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    @PostMapping("/sign")
    public Result sign(ZsWorkRecord entity, OAuth2Authentication oAuth2Authentication){
        return service.start(entity, oAuth2Authentication);
    }

    @PostMapping("/signApp")
    public Result signApp(@RequestBody ZsWorkRecord entity, OAuth2Authentication oAuth2Authentication){
        return service.start(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新 班次
     */
    @PutMapping
    public Result update(@RequestBody ZsWorkRecord entity) {
        return service.updateSignW(entity);
    }

    /**
     * 退出班次
     * @param entity
     * @return
     */
    @GetMapping("/signDown")
    public Result delete(ZsWorkRecord entity){
        return service.deleteSingW(entity);
    }

    /**
     *先根据idNum查询用户，然后根据用户查询其预约时间
     * @param idNum
     * @return
     */
    @AuthIgnore
    @GetMapping("/access/list/{idNum}")
    public Result selectWorkR(@PathVariable String idNum){
        return service.selectWorkR(idNum);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/signDown/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
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
        return "班次报名";
    }
}

