package com.camel.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.system.model.SysNotice;

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
 * <通告MAPPER>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    /**
     * 列表查询
     * @param sysNotice
     * @return
     */
    List<SysNotice> list(SysNotice sysNotice);

    /**
     * 查询最大的排序数字
     * @return
     */
    Integer selectMaxOrderNum();
}
