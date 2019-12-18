package com.camel.survey.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

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
 * <首页>
 * @author baily
 * @since 1.0
 * @date 2019/12/18
 **/
@Data
public class ZsHomeLineChart {
    private List<Map> surveyData;

    private List<Map> collectDatas;

    public ZsHomeLineChart(List<Map> surveyData, List<Map> collectDatas) {
        this.surveyData = surveyData;
        this.collectDatas = collectDatas;
    }

    public ZsHomeLineChart() {
    }
}
