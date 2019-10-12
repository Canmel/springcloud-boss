package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Document;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-10-12
 */
public interface DocumentService extends IService<Document> {
    /**
     * 保存文档
     * @param file
     * @param authentication
     * @return
     */
    int save(MultipartFile file, OAuth2Authentication authentication);

    /**
     * 获取文档地址
     * @param id
     * @return
     */
    String url(Integer id) throws FileNotFoundException;

    /**
     * 分页查询
     * @param document
     * @return 文档分页数据
     */
    PageInfo<Document> selectPage(Document document);
}
