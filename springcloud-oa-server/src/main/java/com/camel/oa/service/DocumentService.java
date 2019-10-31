package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Document;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <文档服务接口>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
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
     * @throws FileNotFoundException
     */
    String url(Integer id) throws FileNotFoundException;

    /**
     * 分页查询
     * @param document
     * @return 文档分页数据
     */
    PageInfo<Document> selectPage(Document document);

    /**
     * 删除文档
     * @param id
     * @return
     * @throws QiniuException
     */
    Integer delete(Integer id) throws QiniuException;
}
