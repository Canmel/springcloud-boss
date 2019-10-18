package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ResourceStatus;
import com.camel.oa.enums.ResourceTyies;
import com.camel.oa.model.Resource;
import com.camel.oa.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * ___====-_  _-====___
 * _--^^^#####//      \\#####^^^--_
 * _-^##########// (    ) \\##########^-_
 * -############//  |\^^/|  \\############-
 * _/############//   (@::@)   \\############\_
 * /#############((     \\//     ))#############\
 * -###############\\    (oo)    //###############-
 * -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 * _#/|##########/\######(   /\   )######/\##########|\#_
 * |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 * `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 * `   `  `      `   / | |  | | \   '      '  '   '
 * (  | |  | |  )
 * __\ | |  | | /__
 * (vvv(VVV)(VVV)vvv)
 * <资源控制器>
 *
 * @author baily
 * @date 2019/10/15
 * @since 1.0
 **/
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseCommonController {

    @Autowired
    private ResourceService service;

    /**
     * 分页查询
     *
     * @param resource
     * @return
     */
    @GetMapping
    public Result index(Resource resource) {
        return ResultUtil.success(service.selectPage(resource));
    }

    /**
     * 新增
     *
     * @param resource
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Resource resource, Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        if (service.save(resource, authentication)) {
            return ResultUtil.success("创建资源成功！");
        }
        return ResultUtil.error(ResultEnum.BAD_REQUEST);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
     * 详细
     *
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
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Resource entity) {
        return super.update(entity);
    }

    /**
     * 资源类型
     *
     * @return
     */
    @GetMapping("/typies")
    public Result typies() {
        return ResultUtil.success(ResourceTyies.all());
    }

    /**
     * 资源状态
     *
     * @return
     */
    @GetMapping("/status")
    public Result status() {
        return ResultUtil.success(ResourceStatus.all());
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "资源";
    }
}

