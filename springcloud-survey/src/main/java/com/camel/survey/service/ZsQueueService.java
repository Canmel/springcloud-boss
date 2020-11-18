package com.camel.survey.service;

import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQueue;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
public interface ZsQueueService extends IService<ZsQueue> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsOption> selectPage(ZsQueue entity);
}
