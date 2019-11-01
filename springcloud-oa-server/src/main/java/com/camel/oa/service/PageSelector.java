package com.camel.oa.service;

import com.camel.oa.model.BaseOaEntity;
import com.github.pagehelper.PageInfo;

public interface PageSelector {
    PageInfo<BaseOaEntity> doSelect();
}
