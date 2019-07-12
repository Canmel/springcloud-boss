package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Imperfect;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-07-12
 */
public interface ImperfectService extends IService<Imperfect> {

    /**
     * 验证行程是否被占用
     * @param errandId
     * @return
     */
    boolean valid(Integer errandId);
}
