package com.camel.realname.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.realname.mapper.NumberManageMapper;
import com.camel.realname.model.TelProtection;
import com.camel.realname.service.NumberManageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NumberManageServiceImpl extends ServiceImpl<NumberManageMapper, TelProtection> implements NumberManageService {
    @Value("${cti.baseUrl}")
    public String baseUrl;

    @Override
    public JSONArray all() {
        String r = HttpUtil.get("http://" + baseUrl + "/yscrm/v2/infs/getpstnnumber.json");
        JSONObject o = JSONUtil.parseObj(r);
        JSONArray array = o.getJSONArray("info");
        return array;
    }
}
