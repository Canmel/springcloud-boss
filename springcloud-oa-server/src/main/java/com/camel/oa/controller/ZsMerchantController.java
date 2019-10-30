package com.camel.oa.controller;
import com.camel.oa.model.ZsProject;
import com.camel.oa.utils.ApplicationToolsUtils;
import org.springframework.web.bind.annotation.GetMapping;
import com.camel.oa.service.ZsMerchantService;
import com.camel.oa.model.ZsMerchant;
import com.camel.core.controller.BaseCommonController;

import com.baomidou.mybatisplus.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;

import java.util.List;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        < 前端控制器>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-10-30
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsMerchant")
public class ZsMerchantController extends BaseCommonController {


    @Autowired
    private ZsMerchantService service;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    /**
    * 分页查询
    */
    @GetMapping
    public Result index(ZsMerchant entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
    * 获取详情
    */
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id){
        ZsMerchant merchant = service.selectById(id);
        merchant.setCreator(applicationToolsUtils.getUser(merchant.getCreatorId()));
        return ResultUtil.success(merchant);
    }

    /**
    * 新建保存
    */
    @PostMapping
    public Result save(@RequestBody ZsMerchant entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsMerchant entity) {
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