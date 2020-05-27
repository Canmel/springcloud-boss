package com.camel.interviewer.service;

import com.camel.interviewer.model.WxSubscibe;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 关注用户 服务类
 * </p>
 *
 * @author baily
 * @since 2020-03-04
 */
public interface WxSubscibeService extends IService<WxSubscibe> {

    boolean save(String toUserName, String eventKey);

    boolean unsave(String toUserName);
}
