package com.camel.realname.service.impl;

import com.camel.realname.mapper.ApproveOrderMapper;
import com.camel.realname.model.ApproveOrder;
import com.camel.realname.service.ApproveOrderService;
import com.camel.realname.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApproveOrderServiceImpl implements ApproveOrderService {
    @Resource
    private ApproveOrderMapper approveOrderMapper;

    @Override
    public Integer addOrder(ApproveOrder approveOrder) {
        return approveOrderMapper.insertOrder(approveOrder);
    }

    @Override
    public PageInfo<ApproveOrder> getPageList(ApproveOrder approveOrder) {
        PageHelper.startPage(approveOrder.getPageNum(),approveOrder.getPageSize());
        Integer uid = ApplicationToolsUtils.getInstance().currentUser().getUid();
        approveOrder.setUserId(uid);
        List<ApproveOrder> list = approveOrderMapper.getList(approveOrder);
        return new PageInfo<>(list);
    }

    @Override
    public Integer pay(Long id) {
        return approveOrderMapper.pay(id);
    }

    @Override
    public ApproveOrder getOne(Long id) {
        return approveOrderMapper.getOneById(id);
    }
}
