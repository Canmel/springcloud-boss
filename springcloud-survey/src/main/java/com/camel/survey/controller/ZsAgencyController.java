package com.camel.survey.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.core.model.ZsAgency;
import com.camel.survey.service.ZsAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/zsAgency")
public class ZsAgencyController extends BaseCommonController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ZsAgencyService zsAgencyService;

    /**
     * 分页查询
     * @param entity
     * @return
     */
    @GetMapping
    private Result index(ZsAgency entity) {
        return ResultUtil.success(zsAgencyService.selectPage(entity));
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    private Result del(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
     * 新建保存
     */
    @PostMapping
    public Result save(ZsAgency entity) {
        return super.save(entity);
    }

    /**
     * 编辑 更新
     * @param entity
     */
    @PutMapping
    public Result update(@RequestBody ZsAgency entity) {
        return super.update(entity);
    }

    @Override
    public IService getiService() {
        return zsAgencyService;
    }

    @Override
    public String getMouduleName() {
        return "结算模板";
    }
}

