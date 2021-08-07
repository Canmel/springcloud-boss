package com.camel.realname.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.config.QiNiuConfig;
import com.camel.realname.enums.ApproveType;
import com.camel.realname.enums.NumberStatus;
import com.camel.realname.mapper.ZsCorpMapper;
import com.camel.realname.model.ZsCorp;
import com.camel.realname.service.ZsCorpService;
import com.camel.realname.utils.ApplicationToolsUtils;
import com.camel.realname.utils.ExportWordUtil;
import com.camel.realname.vo.ZsCorpUrlVo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.IIOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ZsCorpServiceImpl extends ServiceImpl<ZsCorpMapper, ZsCorp> implements ZsCorpService {
    public static final String BUCKET_NAME_URL = "http://image.meedesidy.top";
    public static final String BUCKET_NAME = "c7-oss-store";

    @Resource
    private ApplicationToolsUtils applicationToolsUtils;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ZsCorpMapper zsCorpMapper;

    @Resource
    private QiNiuConfig qiNiuConfig;

    @Override
    public JSONObject upload(MultipartFile file,String imgName) {
        Auth auth = getQiniuAuthentication();
        JSONObject jsonObject = null;
        String upToken = auth.uploadToken(BUCKET_NAME);
        try {
            UploadManager uploadManager = getUploadManager();
            Response response = uploadManager.put(file.getBytes(), null, upToken);
            jsonObject = JSONObject.parseObject(response.bodyString());
            if(jsonObject != null && jsonObject.getString("key") != null){
                Integer userId = applicationToolsUtils.currentUser().getUid();
                ZsCorp zsCorp = new ZsCorp();
                zsCorp.setUserId(userId);
                String name = imgName.substring(0, 1).toUpperCase() + imgName.substring(1);
                Method method = zsCorp.getClass().getDeclaredMethod("set" + name,String.class);
                method.invoke(zsCorp,jsonObject.getString("key"));
                zsCorpMapper.updateCorp(zsCorp);
            }
        } catch (QiniuException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public Result change(ZsCorp zsCorp) {
        Integer uid = applicationToolsUtils.currentUser().getUid();
        zsCorp.setUserId(uid);
        if(zsCorp == null || zsCorp.getId() == null){
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"参数为空，或者id为空");
        }
        Wrapper wrapper = new EntityWrapper<ZsCorp>();
        wrapper.eq("user_id", uid);
        if(update(zsCorp, wrapper)){
            return ResultUtil.success("修改成功");
        }
        return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"修改失败");
    }

    @Override
    public void showImage(Integer userId,ApproveType type,String imgName, HttpServletResponse response) throws FileNotFoundException, IIOException {
        OutputStream out = null;
        InputStream in = null;
        String picUrl = getImageAddr(userId,type,imgName);
        try {
            URL url = new URL(picUrl);
            in = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IIOException("Can't get input stream from URL!",e);
        }
        try {
            response.reset();
            response.setHeader("Content-Type", "image/png");
            out = response.getOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = in.read(b)) != -1){
                out.write(b,0,b.length);
            }
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getImageAddr(Integer id, ApproveType approveType,String fileName) throws FileNotFoundException {
        String redisKey = id+"-type-"+ approveType.getCode() +"-fileName-" + fileName;
        ValueOperations operations = redisTemplate.opsForValue();
        String redisUrl = (String) operations.get(redisKey);
        if(!StringUtils.isNullOrEmpty(redisUrl)) {
            return redisUrl;
        }
        ZsCorp zsCorp = zsCorpMapper.selectById(id);
        if (ObjectUtils.isEmpty(zsCorp)) {
            throw new FileNotFoundException();
        }
        String name = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
        String url = "";
        try {
            //  通过对应的get方法拿到key
            Method method = zsCorp.getClass().getDeclaredMethod("get" + name);
            String key = (String) method.invoke(zsCorp);
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
    public String getImgUrlByKey(String key,String fileName) {
        Integer userId = applicationToolsUtils.currentUser().getUid();
        ZsCorp zsCorp = zsCorpMapper.getOneByUid(userId);
        String redisKey = zsCorp.getId()+"-type-"+ ApproveType.企业.getCode() +"-fileName-" + fileName;
        ValueOperations operations = redisTemplate.opsForValue();
        String publicUrl = String.format("%s/%s", BUCKET_NAME_URL, key);
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
        // 1小时，可以自定义链接过期时间
        String url = auth.privateDownloadUrl(publicUrl, 3600);
        operations.set(redisKey, url, 50 * 60, TimeUnit.SECONDS);
        return url;
    }

    /**
     * 将ZsCorpUrlVo绑定到zsCorp对象中
     * @param zsCorp 企业认证对象
     */
    private void bindZsCorpUrlVo(ZsCorp zsCorp) throws FileNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Integer userId = applicationToolsUtils.currentUser().getUid();
        ZsCorpUrlVo zsCorpUrlVo = new ZsCorpUrlVo();
        Field[] fields = ZsCorpUrlVo.class.getDeclaredFields();
        for (Field field:fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String picUrl = getImageAddr(userId,ApproveType.企业,fieldName);
            String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            zsCorpUrlVo.getClass().getDeclaredMethod("set"+name,String.class).invoke(zsCorpUrlVo,picUrl);
        }
        zsCorp.setZsCorpUrlVo(zsCorpUrlVo);
    }

    @Override
    public Result getOneByUid() throws InvocationTargetException, FileNotFoundException, IllegalAccessException, NoSuchMethodException {
        Integer userId = applicationToolsUtils.currentUser().getUid();
        ZsCorp exist = zsCorpMapper.getOneByUid(userId);
        if(exist == null){
            ZsCorp zsCorp = new ZsCorp();
            zsCorp.setUserId(userId);
            zsCorpMapper.insert(zsCorp);
        }
        ZsCorp zsCorp = zsCorpMapper.getOneByUid(userId);
        bindZsCorpUrlVo(zsCorp);
        return ResultUtil.success("查询成功",zsCorp);
    }

    @Override
    public List<ZsCorp> getList(ZsCorp zsCorp) throws InvocationTargetException, FileNotFoundException,
            IllegalAccessException, NoSuchMethodException {
        List<ZsCorp> list = zsCorpMapper.getList(zsCorp);
        for (ZsCorp zc:list) {
            bindZsCorpUrlVo(zc);
        }
        return list;
    }

    @Override
    public Result audit(ZsCorp zsCorp) {
        Integer res = zsCorpMapper.audit(zsCorp);
        if(res > 0){
            return ResultUtil.success("审核成功");
        }
        return ResultUtil.error(ResultEnum.SERVICE_ERROR);
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

    @Override
    public void exportWord(Integer id, ApproveType approveType, HttpServletResponse response) throws IOException {
        ExportWordUtil ewUtil = new ExportWordUtil();
        Map<String, Object> dataMap = new HashMap<>();
        //企业资质
        String businessLicenseUrl = getImageAddr(id,approveType, "businessLicenseUrl");
        String businessLicense = image2Byte(businessLicenseUrl);
        //法人身份证(正
        String corporateIdZurl = getImageAddr(id,approveType, "corporateIdZurl");
        String corporateIdZ = image2Byte(corporateIdZurl);
        //法人身份证(反
        String corporateIdFurl = getImageAddr(id,approveType, "corporateIdFurl");
        String corporateIdF = image2Byte(corporateIdFurl);
        //法人手持照片
        String corporateIdHurl = getImageAddr(id,approveType, "corporateIdHurl");
        String corporateIdH = image2Byte(corporateIdHurl);
        //经办人身份证 (正
        String agentIdUrl = getImageAddr(id,approveType, "agentIdUrl");
        String agentIdZ = image2Byte(agentIdUrl);
        // （ 反
        String agentIdFurl = getImageAddr(id,approveType, "agentIdFurl");
        String agentIdF = image2Byte(agentIdFurl);
        //经办人手持照片
        String agentIdHurl = getImageAddr(id,approveType, "agentIdHurl");
        String agentIdH = image2Byte(agentIdHurl);
        //电信入网承诺书
        String acceptanceUrl = getImageAddr(id,approveType, "acceptanceUrl");
        String acceptance = image2Byte(acceptanceUrl);
        //号码申请公函
        String entrustmentLetterUrl = getImageAddr(id,approveType, "entrustmentLetterUrl");
        String entrustmentLetter = image2Byte(entrustmentLetterUrl);

        dataMap.put("businessLicense", businessLicense);
        dataMap.put("corporateIdZ", corporateIdZ);
        dataMap.put("corporateIdF", corporateIdF);
        dataMap.put("corporateIdH", corporateIdH);
        dataMap.put("agentIdZ", agentIdZ);
        dataMap.put("agentIdF", agentIdF);
        dataMap.put("agentIdH", agentIdH);
        dataMap.put("acceptance", acceptance);
        dataMap.put("entrustmentLetter", entrustmentLetter);

        ewUtil.exportWord(dataMap, "demo.ftl", response, "全国语音实名材料.doc");
    }

    @Override
    public String image2Byte(String imgUrl) {
        if (imgUrl == null || imgUrl.equals("")){
            return null;
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
