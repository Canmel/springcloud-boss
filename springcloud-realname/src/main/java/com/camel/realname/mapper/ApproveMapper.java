package com.camel.realname.mapper;

import com.camel.realname.model.ApproveInfo;

import java.util.List;

public interface ApproveMapper {
    List<ApproveInfo> getList(ApproveInfo approveInfo);
}
