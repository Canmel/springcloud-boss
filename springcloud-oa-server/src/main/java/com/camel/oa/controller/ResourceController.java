package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ResourceStatus;
import com.camel.oa.enums.ResourceTyies;
import com.camel.oa.model.Resource;
import com.camel.oa.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseCommonController {

    @Autowired
    private ResourceService service;

    /**
     * 分页查询
     * @param resource
     * @return
     */
    @GetMapping
    public Result index(Resource resource) {
        return ResultUtil.success(service.selectPage(resource));
    }

    /**
     * 新增
     * @param resource
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Resource resource) {
        resource.setStatus(ResourceStatus.NORMAL);
        return ResultUtil.success(super.save(resource));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return ResultUtil.success(super.delete(id));
    }

    /**
     * 详细
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        return ResultUtil.success(super.details(id));
    }

    /**
     * 资源类型
     * @return
     */
    @GetMapping("/typies")
    public Result typies() {
        return ResultUtil.success(ResourceTyies.all());
    }

    /**
     * 资源状态
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

