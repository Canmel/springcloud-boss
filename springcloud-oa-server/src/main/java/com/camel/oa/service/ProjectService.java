package com.camel.oa.service;

import com.camel.core.entity.Result;
import com.camel.oa.model.Project;
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
 *               ┃     ┃        <项目 服务类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-10-21
 *                ┗┻┛    ┗┻┛
 */
public interface ProjectService extends IService<Project> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<Project> selectPage(Project entity);

    /**
     * 保存
     * @param entity
     * @param authentication
     * @return
     */
    Result save(Project entity, OAuth2Authentication authentication);

    /**
     * 部署
     * @param id
     * @return
     */
    Result release(Integer id);
}
