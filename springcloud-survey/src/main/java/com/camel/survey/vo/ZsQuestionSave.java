package com.camel.survey.vo;

import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import lombok.Data;

import java.util.ArrayList;
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
 * <接收页面参数>
 * @author baily
 * @since 1.0
 * @date 2019/12/9
 **/
@Data
public class ZsQuestionSave {

    /**
     * 问题列表
     */
    public List<ZsQuestion> zsQuestions;

    /**
     * 选项列表
     */
    public List<ZsOption> zsOptions;

    /**
     * 通过问题的序号获取相对应的选项
     * @param zsQuestion
     * @return
     */
    public List<ZsOption> optionsfilterAndBuildInsertParams(ZsQuestion zsQuestion){
        List<ZsOption> result = new ArrayList<>();
        this.zsOptions.forEach(opt-> {
            if(zsQuestion.getOrderNum().equals(opt.getQuestionOrderNum())){
                opt.setQuestionId(zsQuestion.getId());
                opt.setCreatorId(zsQuestion.getCreatorId());
                result.add(opt);
            }
        });
        return result;
    }
}
