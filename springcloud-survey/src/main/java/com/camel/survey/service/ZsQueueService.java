package com.camel.survey.service;

import cn.hutool.json.JSONArray;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQueue;
import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.cti.Queue;
import com.github.pagehelper.PageInfo;

import java.io.UnsupportedEncodingException;
import java.util.List;

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

    void syncQueue(List<Queue> queues) throws UnsupportedEncodingException;

    void syncQueue() throws UnsupportedEncodingException;

    void push(ZsQueue queue);

    void pull(ZsQueue queue);

    JSONArray selectIVRS();
}
