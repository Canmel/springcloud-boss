package com.camel.realname.mapper;

import com.camel.realname.model.ApproveOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApproveOrderMapper {
    Integer insertOrder(ApproveOrder approveOrder);

    ApproveOrder getOneById(String id);

    Integer pay(String id);

    List<ApproveOrder> getList(ApproveOrder approveOrder);
}
