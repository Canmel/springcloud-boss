package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.config.QiNiuConfig;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.model.Document;
import com.camel.survey.model.ZsReport;
import com.camel.survey.mapper.ZsReportMapper;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsReportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2021-03-17
 */
@Service
public class ZsReportServiceImpl extends ServiceImpl<ZsReportMapper, ZsReport> implements ZsReportService {

    public static final String UPLOAD_DOCUMENT_PATH = "doc/";
    public static final String BUCKET_NAME_URL = "https://image.meedesidy.top";
    public static final String BUCKET_NAME = "c7-oss-store";

    @Autowired
    private ZsReportMapper mapper;

    @Autowired
    private ZsSmsServiceImpl smsService;

    @Autowired
    private QiNiuConfig qiNiuConfig;

    @Override
    public JSONObject save(MultipartFile file) {
        JSONObject jsonObject = upload(file);
        return jsonObject;
    }

    @Override
    public PageInfo<ZsReport> list(ZsReport report) {
        PageInfo pageInfo = PaginationUtil.startPage(report, () -> {
            mapper.list(report);
        });
        List<ZsReport> reports = (List<ZsReport>) pageInfo.getList();
        reports.forEach(r -> {
            r.setGreenUrl(url(r.getGreenUrl()));
            r.setEpidemicUrl(url(r.getEpidemicUrl()));
        });
        return pageInfo;
    }

    @Override
    public String agree(Integer id) {
        ZsReport report = mapper.selectById(id);
        report.setIsPass(ZsYesOrNo.YES);
        if (updateById(report)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            smsService.sendWxMsg(report.getOpenid(), "您好，您申请的" + simpleDateFormat.format(report.getWorkDate()) + " " + report.getWorkShit() + " 在 " + report.getAddress() + " 的兼职已经审核通过，请提前半小时到达");
            return "操作成功";
        }
        return "操作失败";
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
            Response response = getUploadManager().put(file.getInputStream(), null, upToken, null, null);
            jsonObject = JSONObject.parseObject(response.bodyString());
        } catch (QiniuException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
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
     * 获取七牛资源管理器
     *
     * @return
     */
    private BucketManager bucketManager() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        return new BucketManager(getQiniuAuthentication(), cfg);
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
        return new UploadManager(cfg);
    }

    @Override
    public String url(String fileName) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNullOrEmpty(encodedFileName)) {
            return null;
        }
        String publicUrl = String.format("%s/%s", BUCKET_NAME_URL, encodedFileName);
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
        // 1小时，可以自定义链接过期时间
        return auth.privateDownloadUrl(publicUrl, 3600 * 24 * 30);
    }

    @Override
    public String selectSubscribe(String openid) {
        return mapper.selectSubscribe(openid);
    }

    @Override
    public Integer subscribeId(String subscribe) {
        return mapper.subscribeId(subscribe);
    }
}
