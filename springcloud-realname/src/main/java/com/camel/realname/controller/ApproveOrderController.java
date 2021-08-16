package com.camel.realname.controller;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.model.ApproveOrder;
import com.camel.realname.service.ApproveOrderService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/approveOrder")
public class ApproveOrderController {
    @Resource
    private ApproveOrderService approveOrderService;

    /**
     * 查询单个订单
     * @param id 订单编号
     * @return
     */
    @GetMapping("/getOne")
    public Result getOne(@RequestParam("id") String id){
        if(id == null){
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"订单编号为空");
        }
        ApproveOrder order = approveOrderService.getOne(id);
        if(order != null){
            return ResultUtil.success(order);
        }
        return ResultUtil.error(ResultEnum.RESOURCESNOTFOUND.getCode(),"查无该订单");
    }

    /**
     * 根据用户id查询订单列表
     * @param approveOrder
     * @return
     */
    @GetMapping("/getList")
    public Result getList(@Validated(Select.class) ApproveOrder approveOrder){
        PageInfo<ApproveOrder> list = approveOrderService.getPageList(approveOrder);
        if(list != null && list.getSize() > 0){
            return ResultUtil.success(list);
        }
        return ResultUtil.error(ResultEnum.RESOURCESNOTFOUND.getCode(),"查无订单");
    }

}
