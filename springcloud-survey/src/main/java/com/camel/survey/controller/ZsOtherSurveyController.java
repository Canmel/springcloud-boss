package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.model.ZsOtherSurvey;
import com.camel.survey.service.ZsOtherSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 其他平台问卷 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/zsOtherSurvey")
public class ZsOtherSurveyController extends BaseCommonController {

    @Autowired
    private ZsOtherSurveyService service;

    /**
     * 分页查询
     */
    @GetMapping
    public Result index(ZsOtherSurvey entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
    }

    /**
     * 获取详情
     */
    @AuthIgnore
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     */
    @PostMapping
    public Result save(@RequestBody ZsOtherSurvey entity, OAuth2Authentication oAuth2Authentication) throws Exception {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsOtherSurvey entity) throws Exception {
        return service.update(entity);
    }

    @GetMapping("/actives")
    public Result actives() {
        Wrapper wrapper = new EntityWrapper<ZsOtherSurvey>();
        wrapper.eq("state", ZsYesOrNo.YES.getCode());
        return ResultUtil.success(service.selectList(wrapper));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "问卷";
    }
}

