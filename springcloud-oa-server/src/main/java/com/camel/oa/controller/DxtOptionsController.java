package com.camel.oa.controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.camel.oa.service.DxtOptionsService;
import com.camel.oa.model.DxtOptions;
import com.camel.core.controller.BaseCommonController;

import com.baomidou.mybatisplus.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;

import java.util.List;

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
 * <单选题 前端控制器>
 * @author baily
 * @since 1.0
 * @date 2019/10/22
 **/
@RestController
@RequestMapping("/dxtOptions")
public class DxtOptionsController extends BaseCommonController {


    @Autowired
    private DxtOptionsService service;

    /**
    * 分页查询
    */
    @GetMapping
    public Result index(DxtOptions entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
    * 获取详情
    */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id){
        return super.details(id);
    }

    /**
    * 新建保存
    */
    @PostMapping
    public Result save(@RequestBody DxtOptions entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody DxtOptions entity) {
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
        return "";
    }

}