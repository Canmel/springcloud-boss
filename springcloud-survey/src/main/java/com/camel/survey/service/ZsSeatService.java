package com.camel.survey.service;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.survey.model.Args;
import com.camel.survey.model.ZsSeat;
import com.baomidou.mybatisplus.service.IService;
import com.camel.survey.model.ZsSign;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
public interface ZsSeatService extends IService<ZsSeat> {

    PageInfo<ZsSeat> pageQuery(ZsSeat zsSeat);

    ZsSeat selectByUid(Integer id);

    ZsSeat selectLastByUser(Integer id);

    /**
     * 新建
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    Result save(ZsSeat entity, OAuth2Authentication oAuth2Authentication);

    void callbackByUser(int userId);

    ZsSeat selectBySeat(String seat);

    /**
     * 回收坐席，将该坐席的uid清空，state改为0
     * @param id
     * @return
     */
    void callback(Integer id);

    /**
     * 分配坐席，寻找作息表中uid为空的坐席，将第一个坐席分配给该uid对应的用户
     * @param uid
     * @return
     */
    int assignSeat(Integer uid);


    /**
     * 手动分配坐席，寻找作息表中uid为空的坐席，将第一个坐席分配给该uid对应的用户
     * @param entity
     * @param oAuth2Authentication
     * @return
     */
    Result manualAssign(ZsSeat entity, OAuth2Authentication oAuth2Authentication);
}
