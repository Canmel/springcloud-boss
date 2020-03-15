package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.mapper.ZsProjectMapper;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.mapper.ZsWorkMapper;
import com.camel.survey.model.ZsProject;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.model.ZsWork;
import com.camel.survey.service.ZsProjectService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsWorkService;
import com.camel.survey.utils.ExcelUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 访员工作记录 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
@Service
public class ZsWorkServiceImpl extends ServiceImpl<ZsWorkMapper, ZsWork> implements ZsWorkService {
    @Autowired
    private ZsWorkMapper mapper;

    @Autowired
    private ZsSurveyMapper surveyMapper;

    @Autowired
    private ZsSurveyService zsSurveyService;

    @Override
    public boolean importExcel(MultipartFile file) {
        try {
            List<ZsWork> works = ExcelUtil.readExcelObject(file, ZsWork.class);
            List<ZsSurvey> zsSurveyList =  surveyMapper.all();
            for(ZsWork work: works) {
                ZsSurvey zsSurvey = zsSurveyService.getByNameFromList(work.getPname(), zsSurveyList);
                if(!ObjectUtils.isEmpty(zsSurvey)) {
                    work.setProjectId(zsSurvey.getProjectId());
                }
            }
            return insertBatch(works);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("导入时发生错误");
            return false;
        }
    }

    @Override
    public PageInfo<ZsWork> selectPage(ZsWork entity, OAuth2Authentication oAuth2Authentication) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }
}
