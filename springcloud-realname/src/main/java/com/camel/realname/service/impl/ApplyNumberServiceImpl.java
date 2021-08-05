package com.camel.realname.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.config.QiNiuConfig;
import com.camel.realname.enums.NumberStatus;
import com.camel.realname.mapper.ApplyNumberMapper;
import com.camel.realname.model.ApplyNumber;
import com.camel.realname.service.ApplyNumberService;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2021-07-05
 */
@Service
public class ApplyNumberServiceImpl extends ServiceImpl<ApplyNumberMapper, ApplyNumber> implements ApplyNumberService {
    public static final String BUCKET_NAME_URL = "http://image.meedesidy.top";
    public static final String BUCKET_NAME = "c7-oss-store";

    @Autowired
    private ApplyNumberMapper mapper;

    @Autowired
    private QiNiuConfig qiNiuConfig;

    @Override
    public PageInfo<ApplyNumber> list(ApplyNumber entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    public JSONObject upload(MultipartFile file) {
        Auth auth = getQiniuAuthentication();
        JSONObject jsonObject = null;
        String upToken = auth.uploadToken(BUCKET_NAME);
        try {
            UploadManager uploadManager = getUploadManager();
            Response response = uploadManager.put(file.getBytes(), null, upToken);
            jsonObject = JSONObject.parseObject(response.bodyString());
        } catch (QiniuException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 获取七牛上传管理
     *
     * @return
     */
    private UploadManager getUploadManager() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        return uploadManager;
    }

    /**
     * 获取七牛上传凭证
     *
     * @return
     */
    private Auth getQiniuAuthentication() {
        //...生成上传凭证，然后准备上传
        String accessKey = qiNiuConfig.getAccessKey();
        String secretKey = qiNiuConfig.getSecretKey();
        Auth auth = Auth.create(accessKey, secretKey);
        return auth;
    }

    /**
     * 获取url
     * @param id
     * @return url
     * @throws FileNotFoundException
     */
    @Override
    public String url(Integer id) throws FileNotFoundException {
        ApplyNumber applyNumber = mapper.selectById(id);
        if (ObjectUtils.isEmpty(applyNumber)) {
            throw new FileNotFoundException();
        }
        String key = applyNumber.getApplySheet();
        String publicUrl = String.format("%s/%s", BUCKET_NAME_URL, key);
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
        // 1小时，可以自定义链接过期时间
        return auth.privateDownloadUrl(publicUrl, 3600);
    }

    @Override
    public Result apply(Integer id) {
        ApplyNumber applyNumber = mapper.selectById(id);
        if(ObjectUtil.isNotEmpty(applyNumber) && applyNumber.isValid()) {
            applyNumber.setStatus(NumberStatus.APPLYING);
            if(mapper.updateById(applyNumber) > -1) {
                return ResultUtil.success(applyNumber);
            } else {
                return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "申请失败");
            }
        }
        return ResultUtil.error(ResultEnum.NOT_VALID_PARAM.getCode(), "申请失败：请先完成资料");

    }
}
