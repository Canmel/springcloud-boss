package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.oa.model.ZsGround;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <分组服务>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
public interface ZsGroundService extends IService<ZsGround> {
    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    PageInfo selectPage(ZsGround entity);

    /**
     * 保存
     *
     * @param oAuth2Authentication
     * @param entity
     * @return
     */
    Result save(ZsGround entity, OAuth2Authentication oAuth2Authentication);
}
