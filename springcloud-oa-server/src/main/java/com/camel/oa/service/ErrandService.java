package com.camel.oa.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.oa.model.Errand;
import com.camel.oa.model.Reimbursement;
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
 * <出差服务>
 * @author baily
 * @since 1.0
 * @date 2019/7/8
 **/
public interface ErrandService extends IService<Errand> {

    /**
     分页查询出差信息
     @param entity
     @return
     */
    PageInfo<Errand> selectPage(Errand entity);

    /**
     * 获取个人未完善的出差申请单
     * @param uid
     * @return
     */
    List<Errand> imperfect(Integer uid);
}
