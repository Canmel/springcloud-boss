package com.camel.oa.service;

import com.camel.core.entity.Result;
import com.camel.oa.model.Resource;
import com.camel.oa.model.ZsProject;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

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
 *               ┃     ┃        <智慧招商项目 服务类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-10-22
 *                ┗┻┛    ┗┻┛
 */
public interface ZsProjectService extends IService<ZsProject> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo selectPage(ZsProject entity);

    /**
     * 保存
     * @param oAuth2Authentication
     * @param entity
     * @return
     */
    Result save(ZsProject entity, OAuth2Authentication oAuth2Authentication);
}
