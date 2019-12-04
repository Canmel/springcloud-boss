package com.camel.survey.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.survey.model.Vacations;
import com.camel.survey.service.VacationsService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import com.camel.core.controller.BaseCommonController;

import com.baomidou.mybatisplus.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;

import java.util.Calendar;
import java.util.Date;

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
 *               ┃     ┃        <假期表 前端控制器>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-11-27
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/vacations")
public class VacationsController extends BaseCommonController {


    @Autowired
    private VacationsService service;

    /**
    * 分页查询
    */
    @GetMapping
    public Result index(Vacations entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @GetMapping("/year")
    public Result year(Vacations entity) {
        Date date = entity.getVacationDay();
        if(!ObjectUtils.isEmpty(date)) {
            Wrapper wrapper = new EntityWrapper<Vacations>();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            wrapper.eq("year(vacation_day)", c.get(Calendar.YEAR));
            return ResultUtil.success(service.selectList(wrapper));
        }
        return ResultUtil.success("");
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
    public Result save(Vacations entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody Vacations entity) {
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