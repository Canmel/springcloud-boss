package com.camel.realname.controller;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.model.ApproveInfo;
import com.camel.realname.service.ApproveService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/approve")
public class ApproveController {
    @Resource
    private ApproveService approveService;

    /**
     * 分页条件查询认证信息集合
     * @param approveInfo 查询条件
     * @return Result
     */
    @GetMapping("/getList")
    public Result getList(ApproveInfo approveInfo){
        PageInfo<ApproveInfo> pageList = approveService.getPageList(approveInfo);
        return ResultUtil.success("查询成功",pageList);
    }

    @GetMapping("/downInfo/{id}")
    public Result downInfo(@PathVariable("id") Long id, HttpServletResponse response){

        return null;
    }

}
