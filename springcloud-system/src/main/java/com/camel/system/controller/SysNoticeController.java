package com.camel.system.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.system.enums.NoticeStatus;
import com.camel.system.model.SysNotice;
import com.camel.system.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-09-29
 */
@RestController
@RequestMapping("/sysNotice")
public class SysNoticeController extends BaseCommonController {

    @Autowired
    private SysNoticeService service;

    @GetMapping
    public Result index(SysNotice notice) {
        notice.setStatus(NoticeStatus.NORMAL.getCode());
        return ResultUtil.success(service.selectPage(notice));
    }

    @PostMapping
    public Result save(@RequestBody SysNotice sysNotice) {
        return super.save(sysNotice);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        return super.details(id);
    }

    @PutMapping
    public Result update(@RequestBody SysNotice sysNotice) {
        return super.update(sysNotice);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResultUtil.deleteSuccess(getMouduleName());
        } else {
            return ResultUtil.deleteError(getMouduleName());
        }
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "通知";
    }
}

