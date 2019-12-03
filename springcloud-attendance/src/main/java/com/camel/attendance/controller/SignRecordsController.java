package com.camel.attendance.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import com.camel.attendance.service.SignRecordsService;
import com.camel.attendance.model.SignRecords;
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
 *               ┃     ┃        <打卡记录 前端控制器>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-11-27
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/signRecords")
public class SignRecordsController extends BaseCommonController {


    @Autowired
    private SignRecordsService service;

    @GetMapping("/total/{month}")
    public Result total(@PathVariable String month) {
        service.totalByMonth(month);
        return ResultUtil.success("");
    }

    /**
     * 获取某天的考勤记录
     * @param day
     * @return
     */
    @GetMapping("/day/{day}")
    public Result day(@PathVariable String day, OAuth2Authentication oAuth2Authentication){
        return ResultUtil.success(service.day(day, oAuth2Authentication));
    }

    /**
    * 分页查询
    */
    @GetMapping
    public Result index(SignRecords entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    /**
     * 通过月份查询
     *  eg: /month/2019-08-01
     *  取前面7个字符 `2019-08`
     *  获取当月+前后一个月的打卡记录
     */
    @GetMapping("/date/{date}")
    public Result index(@PathVariable String date) {
        return ResultUtil.success(service.selectByMonth(date.split("-")[0], date.split("-")[1]));
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
    public Result save(@RequestBody SignRecords entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody SignRecords entity) {
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