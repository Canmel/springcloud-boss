package com.camel.survey.vo;

import lombok.Data;

import java.util.Date;

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
 * <用户动态>
 * @author baily
 * @since 1.0
 * @date 2019/12/20
 **/
@Data
public class ZsDynamicView {
    private Date createdAt;

    private String username;

    private String active;

    public ZsDynamicView(Date createdAt, String username, String active) {
        this.createdAt = createdAt;
        this.username = username;
        this.active = active;
    }

    public ZsDynamicView() {
    }
}
