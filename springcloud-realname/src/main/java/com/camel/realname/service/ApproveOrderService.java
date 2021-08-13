package com.camel.realname.service;

import com.camel.realname.model.ApproveOrder;
import com.github.pagehelper.PageInfo;

public interface ApproveOrderService {
    /**
     * 新增订单
     */
    Integer addOrder(ApproveOrder approveOrder);

    /**
     * 分页条件查询
     * @param approveOrder
     * @return
     */
    PageInfo<ApproveOrder> getPageList(ApproveOrder approveOrder);

    /**
     * 修改订单为已支付
     * @param id 订单编号
     * @return
     */
    Integer pay(String id);

    /**
     * 根据订单编号查询订单信息
     * @param id
     * @return
     */
    ApproveOrder getOne(String id);
}
