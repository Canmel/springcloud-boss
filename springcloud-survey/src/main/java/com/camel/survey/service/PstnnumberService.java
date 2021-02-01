package com.camel.survey.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

public interface PstnnumberService extends IService {
    JSONArray all();
}
