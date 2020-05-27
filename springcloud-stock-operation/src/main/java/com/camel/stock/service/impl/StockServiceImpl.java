package com.camel.stock.service.impl;

import com.camel.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {

    public static final String SURVEYID = "surveyId";

    public static final String OPTID = "optId";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void reduce(HashMap<String, Object> map) {
        if (map.containsKey(SURVEYID) && !ObjectUtils.isEmpty(map.get(SURVEYID))) {
            Integer surveyId = (Integer) map.get(SURVEYID);
            jdbcTemplate.update("update zs_survey set current_num = current_num - 1 where status = 1 and id = ? ", surveyId);
        }

        if (map.containsKey(OPTID) && !ObjectUtils.isEmpty(map.get(OPTID))) {
            List<Integer> optIds = (List<Integer>) map.get(OPTID);
            Map<String, Object> params = new HashMap<>(16);
            params.put("optionIds", optIds);
            namedParameterJdbcTemplate.update("update zs_option set current = current - 1 where status = 1 and id in (:optionIds)", params);
        }

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(HashMap<String, Object> map) {
        if (map.containsKey(SURVEYID) && !ObjectUtils.isEmpty(map.get(SURVEYID))) {
            Integer surveyId = (Integer) map.get(SURVEYID);
            jdbcTemplate.update("update zs_survey set current_num = current_num + 1 where status = 1 and id = ? ", surveyId);
        }

        if (map.containsKey(OPTID) && !ObjectUtils.isEmpty(map.get(OPTID))) {
            List<Integer> optIds = (List<Integer>) map.get(OPTID);
            Map<String, Object> params = new HashMap<>(16);
            params.put("optionIds", optIds);
            namedParameterJdbcTemplate.update("update zs_option set current = current + 1 where status = 1 and id in (:optionIds)", params);
        }
    }
}
