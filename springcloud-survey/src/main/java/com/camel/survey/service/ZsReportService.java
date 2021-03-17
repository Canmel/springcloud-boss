package com.camel.survey.service;

import com.alibaba.fastjson.JSONObject;
import com.camel.survey.model.ZsReport;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2021-03-17
 */
public interface ZsReportService extends IService<ZsReport> {

    JSONObject save(MultipartFile file);

    String url(String fileName);

    PageInfo<ZsReport> list(ZsReport report);

    String agree(Integer id);
}
