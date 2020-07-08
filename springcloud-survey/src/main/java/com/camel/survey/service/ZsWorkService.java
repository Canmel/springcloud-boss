package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.survey.model.ZsWork;
import com.camel.survey.vo.ProjectReport;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 访员工作记录 服务类
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
public interface ZsWorkService extends IService<ZsWork> {

    Result importExcel(MultipartFile file);

    PageInfo<ZsWork> selectPage(ZsWork zsWork,String[] ids, OAuth2Authentication oAuth2Authentication);

    Result report(ZsWork zsWork);

    PageInfo me(ZsWork entity);

    ProjectReport projectReport(Integer proId);

    void addLog(List<Object> log);

    ProjectReport selectTotalInfoByWork(ZsWork zsWork);
}
