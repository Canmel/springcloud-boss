package com.camel.realname.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.config.QiNiuConfig;
import com.camel.realname.enums.ApproveType;
import com.camel.realname.enums.NumberStatus;
import com.camel.realname.mapper.ApplyNumberMapper;
import com.camel.realname.model.ApplyNumber;
import com.camel.realname.service.ApplyNumberService;
import com.camel.realname.utils.ExportWordUtil;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import com.qiniu.util.StringUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisTemplate redisTemplate;

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
    public String getFileUrl(Integer id, ApproveType type,String fileName) throws FileNotFoundException {
        String redisKey = id+"-type-"+ type.getCode() +"-fileName-" + fileName;
        ValueOperations operations = redisTemplate.opsForValue();
        String redisUrl = (String) operations.get(redisKey);
        if(!StringUtils.isNullOrEmpty(redisUrl)) {
            return redisUrl;
        }
        ApplyNumber applyNumber = mapper.selectById(id);
        if (ObjectUtils.isEmpty(applyNumber)) {
            throw new FileNotFoundException();
        }
        String name = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
        String url = "";
        try {
            //  通过对应的get方法拿到key
            Method method = applyNumber.getClass().getDeclaredMethod("get" + name);
            String key = (String) method.invoke(applyNumber);
            if(key == null || key.equals("")){
                return url;
            }
            String publicUrl = String.format("%s/%s", BUCKET_NAME_URL, key);
            Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
            // 1小时，可以自定义链接过期时间
            url = auth.privateDownloadUrl(publicUrl, 3600);
            operations.set(redisKey, url, 50 * 60, TimeUnit.SECONDS);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return url;
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

    @Override
    public void exportWord(Integer id, ApproveType approveType, HttpServletResponse response) throws IOException {
        ExportWordUtil ewUtil = new ExportWordUtil();
        Map<String, Object> dataMap = new HashMap<>();
        //企业资质
        String license = getFileUrl(id,approveType, "license");
        String businessLicense = image2Byte(license);
        //法人身份证
        String cardLegal = getFileUrl(id,approveType, "cardLegal");
        String corporateId = image2Byte(cardLegal);
        //法人手持照片
        String cardLegalH = getFileUrl(id,approveType, "cardLegalH");
        String corporateIdH = image2Byte(cardLegalH);
        //经办人身份证 (正
        String cardAgent = getFileUrl(id,approveType, "cardAgent");
        String agentId = image2Byte(cardAgent);
        //经办人手持照片
        String handAgent = getFileUrl(id,approveType, "handAgent");
        String agentIdH = image2Byte(handAgent);
        //电信入网承诺书
        String enterPromise = getFileUrl(id,approveType, "enterPromise");
        String acceptance = image2Byte(enterPromise);
        //号码申请公函
        String applyLetter = getFileUrl(id,approveType, "applyLetter");
        String entrustmentLetter = image2Byte(applyLetter);

        dataMap.put("businessLicense", businessLicense);
        dataMap.put("corporateId", corporateId);
        dataMap.put("corporateIdH", corporateIdH);
        dataMap.put("agentId", agentId);
        dataMap.put("agentIdH", agentIdH);
        dataMap.put("acceptance", acceptance);
        dataMap.put("entrustmentLetter", entrustmentLetter);

        ewUtil.exportWord(dataMap, "demo.ftl", response, "全国语音实名材料.doc");
    }

    @Override
    public String image2Byte(String imgUrl) {
        if (imgUrl == null || imgUrl.equals("")){
            return "没有图片";
        }
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestMethod("GET");
            httpUrl.setConnectTimeout(30 * 1000);
            httpUrl.connect();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[2048];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return java.util.Base64.getEncoder().encodeToString(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return null;
    }
}
