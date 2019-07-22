package com.camel.oa.service;


import com.baomidou.mybatisplus.service.IService;
import com.camel.oa.model.Errand;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Trip;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
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
 * <差程补全信息>
 * @author baily
 * @since 1.0
 * @date 2019/7/18
 **/
public interface ImperfectService extends IService<Imperfect> {

    /**
     * 验证行程是否被占用
     * @param errandId
     * @return
     */
    boolean valid(Integer errandId);

    List<Trip> trips(Integer id);


    PageInfo<Imperfect> selectPage(Imperfect imperfect);

    Imperfect getByErrand(Integer id);


}
