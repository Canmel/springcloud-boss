package com.camel.survey.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.ZsCdrinfo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-04-18
 */
public interface ZsCdrinfoService extends IService<ZsCdrinfo> {
    ZsCdrinfo details(String id);

    List<ZsCdrinfo> selectList(Set<String> agents);

    Integer selectAvgTime(Integer id);

    boolean validAndUpdateAnserByTaskAndPhone(ZsCdrinfo zsCdrinfo);

    boolean validAndUpdateByUUID(ZsCdrinfo cdrinfo);

    boolean updateAnswer(Integer answerId, ZsCdrinfo cdrinfo);

    boolean isSaved(ZsCdrinfo cdr);
}
