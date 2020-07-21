package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.survey.model.ZsDelivery;
import com.camel.survey.model.ZsWorkRecord;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
public interface ZsWorkRecordService extends IService<ZsWorkRecord> {

    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsWorkRecord> selectPage(ZsWorkRecord entity);

    Result start(ZsWorkRecord entity, OAuth2Authentication oAuth2Authentication);

    Result updateSignW (ZsWorkRecord entity);

    Result deleteSingW(ZsWorkRecord entity);

    /**
     * 和门禁机器进行交互
     * @param idNum
     * @return
     */
    Result selectWorkR(String idNum);

    List<ZsWorkRecord> selectZsWorkRListByUid(Integer id);
}
