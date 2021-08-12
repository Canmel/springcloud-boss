package com.camel.realname.mapper;

import com.camel.realname.model.ApproveOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApproveOrderMapper {
    Integer insertOrder(ApproveOrder approveOrder);

    ApproveOrder getOneById(Long id);

    Integer pay(Long id);

    List<ApproveOrder> getList(ApproveOrder approveOrder);
}
