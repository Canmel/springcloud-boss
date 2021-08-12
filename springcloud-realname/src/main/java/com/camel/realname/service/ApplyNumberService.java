package com.camel.realname.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.realname.enums.ApproveType;
import com.camel.realname.model.ApplyNumber;
import com.camel.realname.model.TelProtection;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author baily
 * @since 2021-07-05
 */
public interface ApplyNumberService extends IService<ApplyNumber>,ExportService {

    PageInfo<ApplyNumber> list(ApplyNumber entity);

    JSONObject upload(MultipartFile file);

    String getFileUrl(Integer id, ApproveType type, String fileName) throws FileNotFoundException;

    Result apply(Integer id);
}
