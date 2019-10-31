package com.camel.oa.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.model.ZsTalenteder;
import com.camel.oa.service.ZsTalentederService;
import com.camel.oa.utils.ApplicationToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * <人才推荐>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@RestController
@RequestMapping("/zsTalenteder")
public class ZsTalentederController extends BaseCommonController {


    @Autowired
    private ZsTalentederService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    /**
    * 分页查询
    */
    @GetMapping
    public Result index(ZsTalenteder entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
    * 获取详情
    */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id){
        ZsTalenteder talenteder = service.selectById(id);
        talenteder.setCreator(applicationToolsUtils.getUser(talenteder.getCreatorId()));
        return ResultUtil.success(talenteder);
    }

    /**
    * 新建保存
    */
    @PostMapping
    public Result save(@RequestBody ZsTalenteder entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsTalenteder entity) {
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
        return "人才";
    }

}