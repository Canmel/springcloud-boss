package com.camel.survey.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.config.QiNiuConfig;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.ZsReportMapper;
import com.camel.survey.model.ZsReport;
import com.camel.survey.service.ZsReportService;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public HSSFWorkbook download(ZsReport report) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        List<ZsReport> reports = mapper.list(report);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("兼职人员监控表");
        for (int i = 0; i < reports.size(); i++) {
            ZsReport item = reports.get(i);
            String url1 = url(item.getGreenUrl());
            byte[] fileGreenCode = HttpUtil.downloadBytes(url1);
            String url2 = url(item.getEpidemicUrl());
            byte[] fileEpidemicCode = HttpUtil.downloadBytes(url2);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 255, 255, (short) 11, 26 * i + 1, (short) 20, 26 * i + 22);
            anchor1.setAnchorType(3);

            HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 255, 255, (short) 21, 26 * i + 1, (short) 30, 26 * i + 22);
            anchor1.setAnchorType(3);
            patriarch.createPicture(anchor1, wb.addPicture(fileGreenCode, HSSFWorkbook.PICTURE_TYPE_JPEG));
            patriarch.createPicture(anchor2, wb.addPicture(fileEpidemicCode, HSSFWorkbook.PICTURE_TYPE_JPEG));
            int doNum = 0;
            while (doNum < 10) {
                sheet.addMergedRegion(new CellRangeAddress(26 * i + 1, 26 * i + 26, doNum, doNum));
                doNum++;
            }
            HSSFRow row = sheet.createRow(26 * i + 1);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(item.getUsername());
            row.createCell(2).setCellValue(item.getPhone());
            row.createCell(3).setCellValue(item.getSex() > 0 ? "男" : "女");
            row.createCell(4).setCellValue(item.getSchool());
            row.createCell(5).setCellValue(item.getEmail());
            row.createCell(6).setCellValue(item.getAddress());
            row.createCell(7).setCellValue(sf.format(item.getWorkDate()));
            row.createCell(8).setCellValue(item.getWorkShit());
        }
        return wb;
    }
}
