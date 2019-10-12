package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Document;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-10-12
 */
public interface DocumentService extends IService<Document> {
    Document save(MultipartFile file, OAuth2Authentication authentication);

    String url(Integer id);
}
