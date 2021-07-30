package com.camel.realname.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.realname.model.ApplyNumber;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author baily
 * @since 2021-07-05
 */
public interface ApplyNumberService extends IService<ApplyNumber> {

    PageInfo<ApplyNumber> list(ApplyNumber entity);

    JSONObject upload(MultipartFile file);

    Result apply(Integer id);
}