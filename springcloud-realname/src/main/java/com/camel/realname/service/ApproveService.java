package com.camel.realname.service;

import com.camel.realname.model.ApproveInfo;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;

public interface ApproveService {
    /**
     * 分页条件查询认证信息集合
     * @param approveInfo 查询条件
     * @return Result
     */
    PageInfo<ApproveInfo> getPageList(ApproveInfo approveInfo);

    /**
     * 下载
     * @param id
     * @param response
     */
    void downInfo(Long id, HttpServletResponse response);
}
