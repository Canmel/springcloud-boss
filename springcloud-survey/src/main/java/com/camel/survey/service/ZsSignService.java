package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.survey.model.ZsSign;
import com.github.pagehelper.PageInfo;

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
 *               ┃     ┃        <问卷调查报名记录 服务类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-13
 *                ┗┻┛    ┗┻┛
 */
public interface ZsSignService extends IService<ZsSign> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsSign> selectPage(ZsSign entity);



    /**
     * 通过用户ID选择投递记录
     * @param id
     * @return
     */
    Result selectByUserId(Integer id);

    ZsSign selectLastByUser(Integer id);

    List<ZsSign> allInSurvey(Integer id);

    Result userZsSign(String username,String phone);
}
