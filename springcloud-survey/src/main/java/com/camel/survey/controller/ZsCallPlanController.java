package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsCallPlan;
import com.camel.survey.service.ZsCallPlanService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baily
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/zsCallPlan")
public class ZsCallPlanController extends BaseCommonController {
    @Autowired
    private ZsCallPlanService service;

    @GetMapping
    public Result index(ZsCallPlan entity) {
        return ResultUtil.success(service.list(entity));
    }

    @GetMapping("/refresh/{id}")
    public Result refresh(@PathVariable Integer id) {
        return ResultUtil.success(service.refresh(id));
    }

    @PostMapping
    public Result save(ZsCallPlan entity) {
        return service.save(entity);
    }

    /**
     * 启动外呼计划
     * @param id
     * @return
     */
    @GetMapping("/start/{id}")
    public Result startUp(@PathVariable Integer id) {
        service.start(id);
        return ResultUtil.success("外呼计划已启动");
    }

    /**
     * 停止外呼计划
     * @param id
     * @return
     */
    @GetMapping("/end/{id}")
    public Result shutDown(@PathVariable Integer id) {
        service.end(id);
        return ResultUtil.success("外呼计划已停止");
    }

    /**
     * 删除任务
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result del(@PathVariable Integer id) {
        service.del(id);
        return ResultUtil.success("删除任务成功");
    }

    /**
     * 上传号码信息
     * @param file
     * @param id
     * @return
     */
    @PostMapping("/upload/{id}")
    public Result upload(@RequestParam MultipartFile file, @PathVariable Integer id) {
        service.uploadNumers(file, id);
        return ResultUtil.success("号码信息上传成功！");
    }

    @GetMapping("/uploadFromSurvey/{id}")
    public Result uploadFromSurvey(@PathVariable Integer id) {
        ZsCallPlan callPlan = service.selectById(id);
        return ResultUtil.success(service.uploadFromSurvey(callPlan));
    }

    @PutMapping
    public Result edit(@RequestBody ZsCallPlan zsCallPlan) {
        return ResultUtil.success("修改外呼任务成功！");
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "外呼计划";
    }
}

