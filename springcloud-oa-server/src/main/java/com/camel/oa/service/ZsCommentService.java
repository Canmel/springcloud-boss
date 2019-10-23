package com.camel.oa.service;

import com.camel.core.entity.Result;
import com.camel.oa.model.ZsClue;
import com.camel.oa.model.ZsComment;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

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
 *               ┃     ┃        < 服务类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-10-23
 *                ┗┻┛    ┗┻┛
 */
public interface ZsCommentService extends IService<ZsComment> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsComment> selectPage(ZsComment entity);

    /**
     * 保存
     *
     * @param oAuth2Authentication
     * @return
     */
    Result save(ZsComment entity, OAuth2Authentication oAuth2Authentication);

    List<ZsComment> list(ZsComment entity);
}
