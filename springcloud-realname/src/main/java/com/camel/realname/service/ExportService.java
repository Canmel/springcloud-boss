package com.camel.realname.service;

import com.camel.realname.enums.ApproveType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 导出接口
 */
public interface ExportService {
    /**
     * 导出实名认证资料word
     * @param id id
     * @param approveType ApproveType
     * @param response HttpServletResponse
     */
    void exportWord(Integer id,
                    ApproveType approveType,
                    HttpServletResponse response) throws IOException;

    String image2Byte(String imgUrl);
}
