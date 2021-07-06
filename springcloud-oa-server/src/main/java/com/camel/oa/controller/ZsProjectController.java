package com.camel.oa.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ZsProjectIndustryTypies;
import com.camel.oa.enums.ZsProjectLevels;
import com.camel.oa.enums.ZsProjectTypies;
import com.camel.oa.model.ZsProject;
import com.camel.oa.service.ZsProjectService;
import com.camel.oa.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <项目>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@RestController
@RequestMapping("/zsProject")
public class ZsProjectController extends BaseCommonController {


    @Autowired
    private ZsProjectService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    /**
     * 分页查询
     */
    @GetMapping
    public Result index(ZsProject entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 获取详情
     */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        ZsProject project = service.selectById(id);
        project.setManager(applicationToolsUtils.getUser(project.getManagerId()));
        return ResultUtil.success(project);
    }

    /**
     * 新建保存
     */
    @PostMapping
    public Result save(@RequestBody ZsProject entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    @GetMapping("/all")
    public Result all() {
        return ResultUtil.success(service.selectList(new EntityWrapper<ZsProject>()));
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsProject entity) {
        return super.update(entity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
     * 所有项目等级
     */
    @GetMapping("/levels")
    public Result levels() {
        return ResultUtil.success(ZsProjectLevels.all());
    }

    @GetMapping("/typies")
    public Result typies() {
        return ResultUtil.success(ZsProjectTypies.all());
    }

    @GetMapping("/industryTypies")
    public Result industryTypies() {
        return ResultUtil.success(ZsProjectIndustryTypies.all());
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
        return "";
    }

}
