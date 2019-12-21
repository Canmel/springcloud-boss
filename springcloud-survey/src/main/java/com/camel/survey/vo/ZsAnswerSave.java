package com.camel.survey.vo;

import com.camel.survey.exceptions.SurveyFormSaveException;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
 * <回答的总集>
 * @author baily
 * @since 1.0
 * @date 2019/12/17
 **/
@Data
public class ZsAnswerSave {

    /**
     * 调查详细
     */
    public List<ZsAnswerItemSave> zsAnswerItemSaves;

    /**
     * 所属调查
     */
    public Integer surveyId;

    /**
     * 电话
     */
    public String phone;

    /**
     * 坐席
     */
    public String seat;

    /**
     * 录音位置
     */
    public String record;

    public ZsAnswer buildAnswer() {
        ZsAnswer answer = new ZsAnswer(this.surveyId, this.phone);
        answer.setSeat(this.seat);
        answer.setRecord(this.record);
        return answer;
    }

    public ZsAnswerItem buildAnswerItem(ZsQuestion question, ZsOption zsOption, Integer answerId, String value) {
        return new ZsAnswerItem(question.getName(), ObjectUtils.isEmpty(zsOption) ? "" : zsOption.getName(), answerId, value, question.getType(), this.phone);
    }

    public List<ZsAnswerItem> buildAnswerItems(List<ZsQuestion> zsQuestionList, List<ZsOption> optionList, Integer answerId) {
        List<ZsAnswerItem> result = new ArrayList<>();
        zsAnswerItemSaves.forEach(itemSave -> {
            ZsQuestion zsQuestion = null;
            ZsOption zsOption = null;
            if (!StringUtils.isBlank(itemSave.getName())) {
                List<String> nameParams = CollectionUtils.arrayToList(itemSave.getName().split("_"));
                Integer questionId = Integer.parseInt(nameParams.get(1));
                Integer optionId = Integer.parseInt(nameParams.get(2));
                for (ZsQuestion q : zsQuestionList) {
                    if (questionId.equals(q.getId())) {
                        zsQuestion = q;
                    }
                }
                for (ZsOption o : optionList) {
                    if (optionId.equals(o.getId())) {
                        zsOption = o;
                    }
                }
                if (!ObjectUtils.isEmpty(zsQuestion) && StringUtils.isNotBlank(itemSave.getValue())) {
                    result.add(buildAnswerItem(zsQuestion, zsOption, answerId, itemSave.getValue()));
                } else {
                    throw new SurveyFormSaveException();
                }
            } else {
                throw new SurveyFormSaveException();
            }
        });
        return result;
    }
}
