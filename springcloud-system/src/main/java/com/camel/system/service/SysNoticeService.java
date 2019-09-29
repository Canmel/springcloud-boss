package com.camel.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.system.model.SysNotice;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-09-29
 */
public interface SysNoticeService extends IService<SysNotice> {
    /**
     * 分页查询
     * @param sysNotice
     * @return
     */
    PageInfo<SysNotice> selectPage(SysNotice sysNotice);

    /**
     * 删除
     * @param serializable
     * @return
     */
    boolean delete(Serializable serializable);

    /**
     * 将指定ID的通知置顶
     * @param id
     * @return
     */
    boolean top(Integer id);

    /**
     * 推送消息
     * @param id
     * @return
     */
    boolean push(Integer id);
}
