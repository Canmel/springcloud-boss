package com.camel.survey.service;

import com.camel.survey.vo.ZsCrossExport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
 * <导出>
 * @author baily
 * @since 1.0
 * @date 2019/12/24
 **/
public interface ExportService {
    HSSFWorkbook total(Integer surveyId);

    HSSFWorkbook cross(ZsCrossExport zsCrossExport);

    HSSFWorkbook items(Integer surveyId, Integer qId);

    HSSFWorkbook answer(Integer surveyId);

    HSSFWorkbook workNum(Integer id);
}
