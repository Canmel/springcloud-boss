package com.camel.attendance.vo;

import lombok.Data;

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
 * <当天记录>
 * @author baily
 * @since 1.0
 * @date 2019/12/3
 **/
@Data
public class SignDayRecord {
    /**
     * 状态
     */
    private Integer status;

    /**
     * 签到时间
     */
    private String signInDate;

    /**
     * 签退时间
     */
    private String signOutDate;

    /**
     * 考勤日期
     */
    private String signDay;

    public SignDayRecord(String signDay) {
        this.signDay = signDay;
    }

    public SignDayRecord() {
    }
}
