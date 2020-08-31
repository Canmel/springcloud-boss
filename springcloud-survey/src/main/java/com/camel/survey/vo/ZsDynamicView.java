package com.camel.survey.vo;

import lombok.Data;

import java.math.BigDecimal;
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

    private Integer surveyId;

    // 接触量
    private Long tryNum;

    // 成功量
    private BigDecimal successNum;

    // 作废量
    private BigDecimal invalidNum;

    public ZsDynamicView(Date createdAt, String username, String active, Integer surveyId) {
        this.createdAt = createdAt;
        this.username = username;
        this.active = active;
        this.surveyId = surveyId;
    }

    public ZsDynamicView() {
    }
}
