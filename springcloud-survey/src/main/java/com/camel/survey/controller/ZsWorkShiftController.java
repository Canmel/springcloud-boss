package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsWorkShift;
import com.camel.survey.service.ZsWorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    /**
     * 分页查询
     */
    @GetMapping
    public Result index(ZsWorkShift entity, Principal principal) {

        return ResultUtil.success(service.selectPage(entity, principal));
    }



    /**
     * 新建保存
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
     */
    @PutMapping
    public Result update(@RequestBody ZsWorkShift entity) {
        return  service.updateWorkShift(entity);
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
        return "班次";
    }
}

