package com.camel.realname.service.impl;

import com.camel.realname.mapper.ApproveMapper;
import com.camel.realname.model.ApproveInfo;
import com.camel.realname.service.ApproveService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ApproveServiceImpl implements ApproveService {
    @Resource
    private ApproveMapper approveMapper;

    @Override
    public PageInfo<ApproveInfo> getPageList(ApproveInfo approveInfo) {
        PageHelper.startPage(approveInfo.getPageNum(),approveInfo.getPageSize());
        List<ApproveInfo> list = approveMapper.getList(approveInfo);
        return new PageInfo<ApproveInfo>(list);
    }

    @Override
    public void downInfo(Long id, HttpServletResponse response) {

    }
}
