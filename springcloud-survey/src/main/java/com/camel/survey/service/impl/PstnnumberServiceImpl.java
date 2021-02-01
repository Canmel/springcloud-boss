package com.camel.survey.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.service.PstnnumberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PstnnumberServiceImpl extends ServiceImpl implements PstnnumberService {
    @Override
    public JSONArray all() {
        String r = HttpUtil.get("http://tj.svdata.cn/yscrm/v2/infs/getpstnnumber.json");
        JSONObject o = JSONUtil.parseObj(r);
        JSONArray array = o.getJSONArray("info");
        return array;
    }
}
