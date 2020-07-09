package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.model.ZsOtherSurvey;
import com.camel.survey.mapper.ZsOtherSurveyMapper;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsOtherSurveyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 其他平台问卷 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-06-18
 */
@Service
public class ZsOtherSurveyServiceImpl extends ServiceImpl<ZsOtherSurveyMapper, ZsOtherSurvey> implements ZsOtherSurveyService {

    @Autowired
    private ZsOtherSurveyMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsOtherSurvey> selectPage(ZsOtherSurvey entity, OAuth2Authentication oAuth2Authentication) {
        PageInfo pageInfo = PageHelper.startPage(entity.getPageNum(), entity.getPageSize()).doSelectPageInfo(()-> mapper.list(entity));
        return pageInfo;
    }

    @Override
    public Result save(ZsOtherSurvey entity, OAuth2Authentication oAuth2Authentication) throws Exception {
        if(entity.getName()==null||entity.getName().equals(""))
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "问卷名不为空!");
        Wrapper<ZsOtherSurvey> zsOtherSurveyWrapper = new EntityWrapper<>();
        zsOtherSurveyWrapper.eq("name", entity.getName());
        if(selectCount(zsOtherSurveyWrapper)!=0)
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该问卷已存在!");
        SysUser user = applicationToolsUtils.currentUser();
        entity.setCreatorId(user.getUid());
        if (insert(entity)) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }

    @Override
    public Result update(ZsOtherSurvey zsOtherSurvey) throws Exception {
        ZsOtherSurvey zsOtherSurvey1=selectById(zsOtherSurvey.getId());
        if(!ObjectUtils.isEmpty(zsOtherSurvey.getName()) && zsOtherSurvey.getName().equals(""))
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "问卷名不为空!");
        Wrapper<ZsOtherSurvey> zsOtherSurveyWrapper = new EntityWrapper<>();
        zsOtherSurveyWrapper.eq("name", zsOtherSurvey.getName());
        if(selectCount(zsOtherSurveyWrapper)!=0){
            if(!zsOtherSurvey.getName().equals(zsOtherSurvey1.getName()))
                return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该问卷已存在!");
        }
        if (updateById(zsOtherSurvey)) {
            return ResultUtil.success("修改成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "修改失败");
    }
}
