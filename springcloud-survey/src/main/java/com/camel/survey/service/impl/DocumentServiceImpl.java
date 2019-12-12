package com.camel.survey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.model.SysUser;
import com.camel.core.utils.PaginationUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.config.QiNiuConfig;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.mapper.DocumentMapper;
import com.camel.survey.model.Document;
import com.camel.survey.service.DocumentService;
import com.camel.survey.utils.ApplicationToolsUtils;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        < 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-11
 *                ┗┻┛    ┗┻┛
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {

    public static final String UPLOAD_DOCUMENT_PATH = "document/";
    public static final String BUCKET_NAME_URL = "http://image.meedesidy.top";
//    public static final String BUCKET_NAME_URL = "http://pz8qw3bd6.bkt.clouddn.com";
    public static final String BUCKET_NAME = "c7-oss-store";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DocumentMapper mapper;

    @Autowired
    private QiNiuConfig qiNiuConfig;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<Document> selectPage(Document entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });

        List<SysUser> users = applicationToolsUtils.allUsers();
        List<Document> documentList = pageInfo.getList();
        documentList.forEach(document -> {
            users.forEach(user -> {
                if (user.getUid().equals(document.getCreator().getUid())) {
                    document.setCreator(user);
                }
            });
        });
        pageInfo.setList(documentList);
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
        if (!ObjectUtils.isEmpty(jsonObject)) {
            String uploadHashName = jsonObject.getString("hash");
            Document document = new Document(originalFilename, uploadHashName, size.doubleValue(), dtype, member.getId());
            result = mapper.insert(document);
        } else {
            result = -1;
        }
        return result;
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

    /**
     * 获取实体类中的地址
     *
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public String url(Integer id) throws FileNotFoundException {
        Document document = mapper.selectById(id);
        if (ObjectUtils.isEmpty(document)) {
            throw new FileNotFoundException();
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
        String publicUrl = String.format("%s/%s", BUCKET_NAME_URL, encodedFileName);
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
        // 1小时，可以自定义链接过期时间
        return auth.privateDownloadUrl(publicUrl, 3600);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer delete(Integer id) throws QiniuException {
        Integer result = mapper.updateById(new Document(id, ZsStatus.INVALID));
        Document document = mapper.selectById(id);
        this.bucketManager().delete(BUCKET_NAME, document.getAddress());
        return result;
    }
}
