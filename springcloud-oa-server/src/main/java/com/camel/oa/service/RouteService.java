package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Route;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
 * <路程服务>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
public interface RouteService extends IService<Route> {
    PageInfo<Route> selectPage(Route route);

    /**
     * 通过出差Id获取路程
     * @param id
     * @return
     */
    Route getByImperfectId(Integer id);

    /**
     * 查询单个
     * @param route
     * @return
     */
    Route selectOne(Route route);
}
