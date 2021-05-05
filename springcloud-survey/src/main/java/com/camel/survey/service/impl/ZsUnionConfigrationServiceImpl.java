package com.camel.survey.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.model.ZsUnionConfigration;
import com.camel.survey.mapper.ZsUnionConfigrationMapper;
import com.camel.survey.service.ZsOptionService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.ZsUnionConfigrationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2021-05-05
 */
@Service
public class ZsUnionConfigrationServiceImpl extends ServiceImpl<ZsUnionConfigrationMapper, ZsUnionConfigration> implements ZsUnionConfigrationService {
    @Autowired
    private ZsUnionConfigrationMapper mapper;

    @Override
    public int selectValidAnserCountByMultipleOption(List<Integer> options, Integer surveyId) {
        return mapper.selectValidAnserCountByMultipleOption(options, surveyId);
    }

    @Override
    public boolean valid(Integer surveyId, List<ZsOption> options) {
        ZsUnionConfigration unionConfigration = new ZsUnionConfigration();
        unionConfigration.setSurveyId(surveyId);
        List<ZsUnionConfigration> unionConfigrations = involveList(unionConfigration, options);
        if (CollectionUtil.isEmpty(unionConfigrations)) {
            return true;
        }
        for (ZsUnionConfigration configration : unionConfigrations) {
            if (configration.getConfig() > selectValidAnserCountByMultipleOption(configration.getOptionsIntValue(), surveyId)) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public List<ZsUnionConfigration> involveList(ZsUnionConfigration configration, List<ZsOption> options) {
        List<ZsUnionConfigration> result = new ArrayList<>();
        List<ZsUnionConfigration> configrations = mapper.list(configration);
        for (ZsUnionConfigration item : configrations) {
            String[] opts = item.getOptions().split(",");
            boolean r = true;
            if (opts.length > options.size()) {
                r = false;
            } else {
                for (String s : opts) {
                    boolean hasS = false;
                    for (ZsOption option : options) {
                        if (option.getId().equals(Integer.parseInt(s))) {
                            hasS = true;
                            break;
                        }
                    }
                    if (hasS) {
                        r = true;
                    }
                }
            }
            if (r) {
                result.add(item);
            }
        }
        return result;
    }
}
