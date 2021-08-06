package com.camel.realname.mapper;

import com.camel.realname.model.ApproveInfo;

import java.util.List;

public interface ApproveMapper {
    /**
     * 条件查询认证信息集合
     * @param approveInfo 查询条件
     * @return Result
     */
    List<ApproveInfo> getList(ApproveInfo approveInfo);

//    /**
//     * 审核，修改状态
//     * @param approveInfo 信息
//     * @return 受影响行数
//     */
//    Integer audit(ApproveInfo approveInfo);

}
