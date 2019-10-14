package com.camel.oa.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.config.QiNiuConfig;
import com.camel.oa.mapper.DocumentMapper;
import com.camel.oa.model.Document;
import com.camel.oa.service.DocumentService;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-10-12
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {

    public static final String UPLOAD_DOCUMENT_PATH = "document/";
    public static final String BUCKET_NAME = "http://pz8qw3bd6.bkt.clouddn.com";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DocumentMapper mapper;

    @Autowired
    private QiNiuConfig qiNiuConfig;

    @Override
    public PageInfo<Document> selectPage(Document entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public int save(MultipartFile file, OAuth2Authentication authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, authentication.getName());
        Long size = file.getSize();
        String originalFilename = file.getOriginalFilename();
        List<String> list = Arrays.asList(originalFilename.split("\\."));
        String dtype = list.get(list.size() - 1);
        int result;
        JSONObject jsonObject = upload(file);
        if(!ObjectUtils.isEmpty(jsonObject)) {
            String uploadHashName = jsonObject.getString("hash");
            Document document = new Document(originalFilename, uploadHashName, size.doubleValue(), dtype, member.getId());
             result = mapper.insert(document);
        }else {
            result = -1;
        }
        return result;
    }

    public JSONObject upload(MultipartFile file) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = qiNiuConfig.getAccessKey();
        String secretKey = qiNiuConfig.getSecretKey();
        String bucket = "c7-oss-store";
        JSONObject jsonObject = null;
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(file.getInputStream(), null, upToken, null, null);
                //解析上传成功的结果
                System.out.println(response.bodyString());
                jsonObject = JSONObject.parseObject(response.bodyString());
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return jsonObject;
    }

    /**
     * 获取实体类中的地址
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public String url(Integer id) throws FileNotFoundException {
        Document document = mapper.selectById(id);
        if(ObjectUtils.isEmpty(document)) {
            throw  new FileNotFoundException();
        }
        String fileName = document.getAddress();
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNullOrEmpty(encodedFileName)) {
            return null;
        }
        String publicUrl = String.format("%s/%s", BUCKET_NAME, encodedFileName);
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
        // 1小时，可以自定义链接过期时间
        return auth.privateDownloadUrl(publicUrl, 3600);
    }
}
